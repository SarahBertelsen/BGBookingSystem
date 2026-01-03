package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BoardGameCopyCtrl;
import ctrl.BookingCtrl;
import db.BoardGameCopyDAO;
import db.BoardGameCopyDB;
import db.BookingDAO;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.InvalidFormatException;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.BoardGame;
import model.BoardGameCopy;
import model.Category;
import model.Level;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class FindAvailableBoardGameFrame extends JFrame implements FrameIF {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField playersTxt;
	private JTextField durationTxt;
	private JTable boardGameTable;
	private JComboBox levelComboBox;
	private JComboBox categoryComboBox;

	private BoardGameTableModel bgtm;
	private GameBasketTableModel gbtm;
	private BookingCtrl bookingCtrl;
	private BoardGameCopyCtrl bgCopyCtrl;
	private MainFrame mainFrame;
	private List<BoardGame> games;
	private List<BoardGameCopy> bgCopies;
	private List<BoardGameCopy> reservedCopies;
	private JTable reservedCopiesTable;

	private Thread thread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDB cusDb = new CustomerDB();
					TableDB tableDb = new TableDB();
					BoardGameCopyDB bgCopyDb = new BoardGameCopyDB();
					MembershipDAO memDb = new MembershipDB();
					BookingDAO bookingDb = new BookingDB(bgCopyDb, tableDb, cusDb, memDb);
					BoardGameCopyCtrl bgCopyCtrl = new BoardGameCopyCtrl(bookingDb, bgCopyDb);
					BookingCtrl bookingCtrl = new BookingCtrl(new BookingDB(bgCopyDb, tableDb, cusDb, memDb), cusDb,
							tableDb, memDb);
					bookingCtrl.createBooking(LocalDateTime.now().plusHours(12), 4);
					FindAvailableBoardGameFrame frame = new FindAvailableBoardGameFrame(bookingCtrl, bgCopyCtrl, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FindAvailableBoardGameFrame(BookingCtrl bookingCtrl, BoardGameCopyCtrl bgCopyCtrl, MainFrame mainFrame) {
		this.bookingCtrl = bookingCtrl;

		this.mainFrame = mainFrame;
		this.bgCopyCtrl = bgCopyCtrl;
		this.bgCopies = new ArrayList<>();
		this.games = new ArrayList<>();
		this.reservedCopies = new ArrayList<>();
		this.bgtm = new BoardGameTableModel(games);
		this.gbtm = new GameBasketTableModel(reservedCopies);
		this.thread = new Thread(new AvailableBoardGameRunnable(this));
		thread.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel gameNameLbl = new JLabel("Navn på spil");
		gameNameLbl.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_gameNameLbl = new GridBagConstraints();
		gbc_gameNameLbl.insets = new Insets(0, 0, 5, 0);
		gbc_gameNameLbl.gridx = 0;
		gbc_gameNameLbl.gridy = 0;
		panel.add(gameNameLbl, gbc_gameNameLbl);

		nameTxt = new JTextField();
		nameTxt.setFont(new Font("Tahoma", Font.ITALIC, 10));
		GridBagConstraints gbc_nameTxt = new GridBagConstraints();
		gbc_nameTxt.insets = new Insets(0, 0, 5, 0);
		gbc_nameTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTxt.gridx = 0;
		gbc_nameTxt.gridy = 1;
		panel.add(nameTxt, gbc_nameTxt);
		nameTxt.setColumns(10);

		JLabel playerLbl = new JLabel("Antal spillere");
		playerLbl.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_playerLbl = new GridBagConstraints();
		gbc_playerLbl.insets = new Insets(0, 0, 5, 0);
		gbc_playerLbl.gridx = 0;
		gbc_playerLbl.gridy = 2;
		panel.add(playerLbl, gbc_playerLbl);

		playersTxt = new JTextField();
		GridBagConstraints gbc_playersTxt = new GridBagConstraints();
		gbc_playersTxt.insets = new Insets(0, 0, 5, 0);
		gbc_playersTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_playersTxt.gridx = 0;
		gbc_playersTxt.gridy = 3;
		panel.add(playersTxt, gbc_playersTxt);
		playersTxt.setColumns(10);

		JLabel durationLbl = new JLabel("Varighed på spil");
		durationLbl.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_durationLbl = new GridBagConstraints();
		gbc_durationLbl.insets = new Insets(0, 0, 5, 0);
		gbc_durationLbl.gridx = 0;
		gbc_durationLbl.gridy = 4;
		panel.add(durationLbl, gbc_durationLbl);

		durationTxt = new JTextField();
		GridBagConstraints gbc_durationTxt = new GridBagConstraints();
		gbc_durationTxt.insets = new Insets(0, 0, 5, 0);
		gbc_durationTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_durationTxt.gridx = 0;
		gbc_durationTxt.gridy = 5;
		panel.add(durationTxt, gbc_durationTxt);
		durationTxt.setColumns(10);

		JLabel levelLbl = new JLabel("Sværhedsgrad");
		levelLbl.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_levelLbl = new GridBagConstraints();
		gbc_levelLbl.insets = new Insets(0, 0, 5, 0);
		gbc_levelLbl.gridx = 0;
		gbc_levelLbl.gridy = 6;
		panel.add(levelLbl, gbc_levelLbl);

		levelComboBox = new JComboBox<>(Level.values());
		levelComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_levelComboBox = new GridBagConstraints();
		gbc_levelComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_levelComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_levelComboBox.gridx = 0;
		gbc_levelComboBox.gridy = 7;
		panel.add(levelComboBox, gbc_levelComboBox);

		JLabel categoryLbl = new JLabel("Kategori");
		categoryLbl.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_categoryLbl = new GridBagConstraints();
		gbc_categoryLbl.insets = new Insets(0, 0, 5, 0);
		gbc_categoryLbl.gridx = 0;
		gbc_categoryLbl.gridy = 8;
		panel.add(categoryLbl, gbc_categoryLbl);

		categoryComboBox = new JComboBox<>(Category.values());
		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.gridx = 0;
		gbc_categoryComboBox.gridy = 9;
		panel.add(categoryComboBox, gbc_categoryComboBox);

		JButton searchBtn = new JButton("Søg efter ledige spil");
		searchBtn.addActionListener(e -> searchClicked());
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_searchBtn = new GridBagConstraints();
		gbc_searchBtn.insets = new Insets(0, 0, 5, 0);
		gbc_searchBtn.gridx = 0;
		gbc_searchBtn.gridy = 10;
		panel.add(searchBtn, gbc_searchBtn);

		JLabel titleLbl = new JLabel("Tilføj brætspil");
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(titleLbl, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		boardGameTable = new JTable(bgtm);
		boardGameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(boardGameTable);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		reservedCopiesTable = new JTable(gbtm);
		reservedCopiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(reservedCopiesTable);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JButton btnNewButton = new JButton("Vis beskrivelse af spil");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel_3.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.addActionListener(e -> displayInfoClicked());
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.EAST);

		JLabel continueLbl = new JLabel("Du kan fortsætte uden at tilføje brætspil");
		panel_4.add(continueLbl);
		continueLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));

		JButton selectBoardGameBtn = new JButton("Tilføj spil til booking");
		panel_4.add(selectBoardGameBtn);
		selectBoardGameBtn.addActionListener(e -> addBoardGameClicked());

		selectBoardGameBtn.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton removeReservedCopyBtn = new JButton("Fjern spil fra booking");
		panel_4.add(removeReservedCopyBtn);
		removeReservedCopyBtn.addActionListener(e -> removeReservedCopyClicked());
		removeReservedCopyBtn.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton continueBtn = new JButton("Fortsæt");
		continueBtn.addActionListener(e -> continueClicked());
		continueBtn.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel_4.add(continueBtn);

		searchClicked();
	}
	
	/**
	 * Used when search button is clicked to update the list of found games.
	 * 
	 * 
	 */
	public void searchClicked() {
		updateGameList();
		if (bgCopies.isEmpty()) {
			JOptionPane.showMessageDialog(this, InvalidFormatException.INVALID_GAMES);
		}

	}
	
	/**
	 * Updates the list of boardgames by getting copies based on search criteria from the ui, and then makes sure not to display multiple copies.
	 * 
	 */
	public synchronized void updateGameList() {
		setBgCopies(findBoardGameCopies());
		Set<Integer> reservedBoardGameIds= reservedCopies.stream()
				.map(BoardGameCopy::getBoardGame)
	            .map(BoardGame::getBoardGameId)
	            .collect(Collectors.toSet());
		
		games = bgCopies.stream().map(BoardGameCopy::getBoardGame)
				.filter(bg -> !reservedBoardGameIds.contains(bg.getBoardGameId()))
				.distinct()
				.sorted(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER))
				.collect(Collectors.toList());
		
		bgtm.setData(games);
	}


	/**
	 * Gets a list of board game copies from the data base using criteria from the ui.
	 * @return A list of sorted boardgame copies.
	 */
	public List<BoardGameCopy> findBoardGameCopies() {
		String name = getName();
		int players = getNoOfPlayers();
		int duration = getDuration();
		Level level = getSelectedLevel();
		Category category = getSelectedCategory();
		LocalDateTime date = bookingCtrl.getCurrentBooking().getDate();
		
		return bgCopyCtrl.findAvailableBoardGameCopies(date, name, players, level, category, duration);
	}
	
	/**
	 * When add board button is pressed, tries to add it to the basket.
	 * Also updates the list of boardgames, removing the just reserved title.
	 */
	public void addBoardGameClicked() {
		BoardGame game = getSelectedGame();
		if (game == null)
			return;
		
		

		BoardGameCopy reservedCopy = bgCopies.stream().filter(bgCopy -> bgCopy.getBoardGame().equals(game)).findFirst()
				.orElse(null);

		addToBasket(reservedCopy);
		
		updateGameList();
	}
	
	/**
	 * Puts a temporary reserve on a boardgame copy and adds it the basket.
	 * Will only add a copy if there aren't already 3 copies in the basket.
	 * 
	 * @param reservedCopy The copy that gets added to the basket.
	 */
	public void addToBasket(BoardGameCopy reservedCopy) {
		if (reservedCopies.size() >= 3) {
			JOptionPane.showMessageDialog(this, guiMessages.BG_COPY_BOOKING_FULL);
		} else if (reservedCopy != null && !reservedCopies.contains(reservedCopy)) {
			reservedCopies.add(reservedCopy);
			gbtm.setData(reservedCopies);

			addReservation(reservedCopy, bookingCtrl.getCurrentBooking().getDate());
		}
	}
	
	/**
	 * creates a temporary reservation on the boardgame copy.
	 * 
	 * @param bgCopy the copy that gets reserved.
	 * @param date the date it gets reserved for.
	 */
	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		bgCopyCtrl.addReservation(bgCopy, bookingCtrl.getCurrentBooking().getDate());
	}

	/**
	 * Removes a temporary reservation on the boardgame copy.
	 * 
	 * @param bgCopy the copy that gets unreserved.
	 * @param date the date it gets unreserved for.
	 */
	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date) {
		bgCopyCtrl.removeReservation(bgCopy, bookingCtrl.getCurrentBooking().getDate());
	}
	
	/**
	 * Gets the selected game from the available boardgames row.
	 * 
	 * @return Returns the selected game. Returns null if no game is selected.
	 */
	public BoardGame getSelectedGame() {
		int rowIndex = boardGameTable.getSelectedRow();
		BoardGame res = null;
		if (rowIndex >= 0) {
			res = bgtm.getDataAt(rowIndex);
		}
		
		return res;
	}

	/**
	 * Gets the selected boardgamecopy from the basket
	 * 
	 * @return the selected copy from the basket.
	 */
	public BoardGameCopy getSelectedReservedCopy() {
		int rowIndex = reservedCopiesTable.getSelectedRow();
		BoardGameCopy res = gbtm.getDataAt(rowIndex);
		return res;
	}

	/**
	 * When the removeReservedCopyButton this removes it from the basket, undoes the reservation and updates the available games list to feature it again.
	 * 
	 */
	public void removeReservedCopyClicked() {
		BoardGameCopy reservedCopy = getSelectedReservedCopy();
		if (reservedCopy != null) {
			reservedCopies.remove(reservedCopy);
			gbtm.setData(reservedCopies);
			reservedCopiesTable.setModel(gbtm);
			removeReservation(reservedCopy, bookingCtrl.getCurrentBooking().getDate());
			updateGameList();
		}
	}

	/**
	 * Creates and displays the boardgameinfodialog for the selected boardgame, from the available boardgames list.
	 */
	public void displayInfoClicked() {
		BoardGame game = getSelectedGame();
		DisplayBoardGameInfoDialog dialog = new DisplayBoardGameInfoDialog(this, game);
		dialog.setVisible(true);
	}

	/**
	 * Adds the boardgameCopies from the basket, the order and opens the customerDetailsFrame.
	 * 
	 */
	public void continueClicked() {
		if (reservedCopies.size() <= 3) {
			for (BoardGameCopy bgCopy : reservedCopies) {
				bookingCtrl.addBoardGameCopy(bgCopy);
			}
		} else {
			JOptionPane.showMessageDialog(this, InvalidFormatException.INVALID_NO_OF_GAMES);
		}
		mainFrame.addCustomerDetails();
	}

	/**
	 * Gets the name from the name search box.
	 * 
	 * @return Returns the trimmed name string from the ui. If the String is empty null is returned instead.
	 */
	public String getName() {
		String result = nameTxt.getText().trim();
		if (result.isEmpty()) {
			result = null;
		}
		return result;
	}

	/**
	 * Gets the no of players from the noOfPlayers search box.
	 * 
	 * @return Returns the number of players. If the String is empty 0 is returned instead.
	 */
	public int getNoOfPlayers() {
		String txt = playersTxt.getText().trim();
		if (txt.isEmpty()) {
			return 0;
		}
		try {
			return Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Gets the duration from the duration search box.
	 * 
	 * @return Returns the duration. If the String is empty 0 is returned instead.
	 */
	public int getDuration() {
		String txt = durationTxt.getText().trim();
		if (txt.isEmpty()) {
			return 0;
		}
		try {
			return Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Gets the level from the level search box.
	 * 
	 * @return Returns the level.
	 */
	public Level getSelectedLevel() {
		return (Level) levelComboBox.getSelectedItem();
	}

	/**
	 * Gets the Category from the category search box.
	 * 
	 * @return Returns the category.
	 */
	public Category getSelectedCategory() {
		return (Category) categoryComboBox.getSelectedItem();
	}

	public List<BoardGameCopy> getBgCopies() {
		return bgCopies;
	}

	public synchronized void setBgCopies(List<BoardGameCopy> bgCopies) {
		this.bgCopies = bgCopies;
	}

	@Override
	public void enter() {
		setVisible(true);
		
	}

	@Override
	public void exit() {
		setVisible(false);
		
	}

	@Override
	public void close() {
		exit();
		for (BoardGameCopy bgCopy : reservedCopies) {
			bgCopyCtrl.removeReservation(bgCopy, bookingCtrl.getCurrentBooking().getDate());
		}
		dispose();
		thread.interrupt();
	}
}
