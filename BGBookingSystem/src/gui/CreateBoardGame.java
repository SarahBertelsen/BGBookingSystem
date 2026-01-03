package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BoardGameCopyCtrl;
import db.BoardGameCopyDAO;
import db.BoardGameCopyDB;
import db.BoardGameDAO;
import db.BoardGameDB;
import db.BookingDAO;
import db.BookingDB;
import db.CustomerDAO;
import db.CustomerDB;
import db.MembershipDAO;
import db.MembershipDB;
import db.TableDAO;
import db.TableDB;
import model.BoardGame;
import model.BoardGameCopy;
import model.Category;
import model.Level;
import gui.FrameIF;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class CreateBoardGame extends JFrame implements FrameIF {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titleTextField;
	private JTextField noOfPlayerstextField;
	private JTextField durationTextField;
	private BoardGameDAO bgDAO;
	private JTextArea descriptionTextArea;
	private JComboBox<Level> levelComboBox;
	private JComboBox<Category> categoryComboBox;
	private BoardGameCopyDAO bgCopyDao;
	private BookingDAO bookingDao;
	private TableDAO tableDao;
	private CustomerDAO customerDao;
	private MembershipDAO membershipDao;
	private BoardGameDAO boardGameDao;
	private MainFrame mainFrame;
	private JTextField noOfCopiesTxt;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableDAO tableDao = new TableDB();
			        CustomerDAO customerDao = new CustomerDB();
			        MembershipDAO membershipDao = new MembershipDB();
			        BoardGameCopyDAO bgCopyDao = new BoardGameCopyDB();
			        BoardGameDAO boardGameDao = new BoardGameDB();
			        BookingDAO bookingDao = new BookingDB(bgCopyDao, tableDao, customerDao, membershipDao);
					CreateBoardGame frame = new CreateBoardGame(bgCopyDao, bookingDao, tableDao, customerDao, membershipDao, boardGameDao, null);
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
	public CreateBoardGame(BoardGameCopyDAO bgCopyDao, BookingDAO bookingDao, TableDAO tableDao, CustomerDAO customerDao, MembershipDAO membershipDao, BoardGameDAO boardGameDao, MainFrame mainFrame) {
		this.bgCopyDao = bgCopyDao;
		this.bookingDao = bookingDao;
		this.tableDao = tableDao;
		this.customerDao = customerDao;
		this.membershipDao = membershipDao;
		this.boardGameDao = boardGameDao;
		this.mainFrame = mainFrame;
		
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
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblGameName = new JLabel("Navn på Spillet");
		GridBagConstraints gbc_lblGameName = new GridBagConstraints();
		gbc_lblGameName.insets = new Insets(0, 0, 5, 0);
		gbc_lblGameName.gridx = 0;
		gbc_lblGameName.gridy = 0;
		panel.add(lblGameName, gbc_lblGameName);
		
		titleTextField = new JTextField();
		titleTextField.setColumns(10);
		GridBagConstraints gbc_titleTextField = new GridBagConstraints();
		gbc_titleTextField.insets = new Insets(0, 0, 5, 0);
		gbc_titleTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_titleTextField.gridx = 0;
		gbc_titleTextField.gridy = 1;
		panel.add(titleTextField, gbc_titleTextField);
		
		JLabel lblLevel = new JLabel("Level");
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.insets = new Insets(0, 0, 5, 0);
		gbc_lblLevel.gridx = 0;
		gbc_lblLevel.gridy = 2;
		panel.add(lblLevel, gbc_lblLevel);
		
		levelComboBox = new JComboBox<>(Level.values());
		GridBagConstraints gbc_levelComboBox = new GridBagConstraints();
		gbc_levelComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_levelComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_levelComboBox.gridx = 0;
		gbc_levelComboBox.gridy = 3;
		panel.add(levelComboBox, gbc_levelComboBox);
		
		JLabel lblNumberOfPlayers = new JLabel("Antal Spillere ");
		GridBagConstraints gbc_lblNumberOfPlayers = new GridBagConstraints();
		gbc_lblNumberOfPlayers.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumberOfPlayers.gridx = 0;
		gbc_lblNumberOfPlayers.gridy = 4;
		panel.add(lblNumberOfPlayers, gbc_lblNumberOfPlayers);
		
		noOfPlayerstextField = new JTextField();
		noOfPlayerstextField.setColumns(10);
		GridBagConstraints gbc_noOfPlayerstextField = new GridBagConstraints();
		gbc_noOfPlayerstextField.insets = new Insets(0, 0, 5, 0);
		gbc_noOfPlayerstextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_noOfPlayerstextField.gridx = 0;
		gbc_noOfPlayerstextField.gridy = 5;
		panel.add(noOfPlayerstextField, gbc_noOfPlayerstextField);
		
		JLabel lblCategory = new JLabel("Category");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.insets = new Insets(0, 0, 5, 0);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 6;
		panel.add(lblCategory, gbc_lblCategory);
		
		categoryComboBox = new JComboBox<>(Category.values());
		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.gridx = 0;
		gbc_categoryComboBox.gridy = 7;
		panel.add(categoryComboBox, gbc_categoryComboBox);
		
		JLabel lblDuration = new JLabel("Spil Længde");
		GridBagConstraints gbc_lblDuration = new GridBagConstraints();
		gbc_lblDuration.insets = new Insets(0, 0, 5, 0);
		gbc_lblDuration.gridx = 0;
		gbc_lblDuration.gridy = 8;
		panel.add(lblDuration, gbc_lblDuration);
		
		durationTextField = new JTextField();
		durationTextField.setColumns(10);
		GridBagConstraints gbc_durationTextField = new GridBagConstraints();
		gbc_durationTextField.gridx = 0;
		gbc_durationTextField.gridy = 9;
		gbc_durationTextField.weightx = 1.0; 
		gbc_durationTextField.fill = GridBagConstraints.HORIZONTAL;
		panel.add(durationTextField, gbc_durationTextField);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		descriptionTextArea = new JTextArea();
		scrollPane.setViewportView(descriptionTextArea);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblDescription);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.EAST);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblnoOfKopier = new JLabel("Mængde af Kopier");
		GridBagConstraints gbc_lblnoOfKopier = new GridBagConstraints();
		gbc_lblnoOfKopier.anchor = GridBagConstraints.EAST;
		gbc_lblnoOfKopier.insets = new Insets(0, 0, 0, 5);
		gbc_lblnoOfKopier.gridx = 8;
		gbc_lblnoOfKopier.gridy = 0;
		panel_2.add(lblnoOfKopier, gbc_lblnoOfKopier);
		
		noOfCopiesTxt = new JTextField();
		GridBagConstraints gbc_noOfCopiesTxt = new GridBagConstraints();
		gbc_noOfCopiesTxt.insets = new Insets(0, 0, 0, 5);
		gbc_noOfCopiesTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_noOfCopiesTxt.gridx = 9;
		gbc_noOfCopiesTxt.gridy = 0;
		panel_2.add(noOfCopiesTxt, gbc_noOfCopiesTxt);
		noOfCopiesTxt.setColumns(10);
		
		JButton btnCreateCopies = new JButton("Opret Brætspil");
		GridBagConstraints gbc_btnCreateCopies = new GridBagConstraints();
		gbc_btnCreateCopies.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreateCopies.gridx = 10;
		gbc_btnCreateCopies.gridy = 0;
		panel_2.add(btnCreateCopies, gbc_btnCreateCopies);
		
		btnCreateCopies.addActionListener(e -> noOfCopiesClicked());
		
	}
	
	/**
	 * Uses the information from the ui to create and save a boardgame and its copies in the database.
	 * 
	 * @return Returns the saved BoardGame with updated id
	 * @throws SQLException
	 */
	public BoardGame saveBoardGame() throws SQLException {
		String name = titleTextField.getText();
		Level level = (Level) levelComboBox.getSelectedItem();
		Category category = (Category) categoryComboBox.getSelectedItem();
		int numberOfPlayers = Integer.parseInt(noOfPlayerstextField.getText());
		int durationInMinutes = Integer.parseInt(durationTextField.getText());
		String description = descriptionTextArea.getText();
		
		BoardGame bg = new BoardGame(-1, name, level, numberOfPlayers, category, durationInMinutes, description);
		boardGameDao.createBoardGame(bg);
		
		return bg;
	}
	
	/**
	 * Uses info from the ui to create a board game and its copies.
	 * 
	 * 
	 */
	public void noOfCopiesClicked(){
		try {
			//laver et brætspil
			BoardGame bg = saveBoardGame();
			
			// tager input af antal kopier fra brugeren
			String copyInput = noOfCopiesTxt.getText().trim();
			
			// Converter input fra brugeren til et tal
			int amountOfCopies;
			try {
				amountOfCopies = Integer.parseInt(copyInput);
				if (amountOfCopies <= 0) {
				    JOptionPane.showMessageDialog(this, "Antallet skal være større end 0!");
				    return;
				}
			} catch (NumberFormatException nfe) {
			    JOptionPane.showMessageDialog(this, "Kun tal er tilladt!");
			    return;
			}
			
			
			BoardGameCopyCtrl bgcCtrl = new BoardGameCopyCtrl(bookingDao, bgCopyDao);
			
			//copyId er sat til -1 så Databasen generere en id
			for(int i = 0; i < amountOfCopies; i++) {
				int copyId = -1;
				BoardGameCopy bgCopy = new BoardGameCopy(copyId, bg);
				bgcCtrl.addBoardGameCopy(bgCopy);
			}
			
			JOptionPane.showMessageDialog(this, "Kopier er blevet oprettet");
			
			mainFrame.returnToMain();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Kunne ikke oprette kopier" + e.getMessage());
		}
		
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
		dispose();
	}
	
}
