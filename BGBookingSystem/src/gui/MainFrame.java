package gui;

import java.awt.EventQueue;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctrl.BoardGameCopyCtrl;
import ctrl.BookingCtrl;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame implements GUIIF{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Stack<FrameIF> frameStack = new Stack<>();
	
	private BoardGameCopyDAO bgCopyDao = new BoardGameCopyDB();
	private CustomerDAO customerDao = new CustomerDB();
	private TableDAO tableDao = new TableDB();
	private MembershipDAO membershipDao = new MembershipDB();
	private BookingDAO bookingDao = new BookingDB(bgCopyDao, tableDao, customerDao, membershipDao);
	private BoardGameCopyCtrl bgCopyCtrl = new BoardGameCopyCtrl(bookingDao, bgCopyDao);
	private BookingCtrl bookingCtrl = new BookingCtrl(bookingDao, customerDao, tableDao, membershipDao);
	private BoardGameDAO boardGameDao = new BoardGameDB();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel lblNewLabel = new JLabel("Velkommen til Operaen!");
		lblNewLabel.setFont(new Font("Sylfaen", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton bookTableButton = new JButton("Book Bord");
		bookTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createBooking();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		panel.add(bookTableButton, gbc_btnNewButton);
		
		JButton boardGamesButton = new JButton("Brætspil");
		boardGamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crudBoardGame();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 3;
		panel.add(boardGamesButton, gbc_btnNewButton_1);
		
		setVisible(true);
	}

	/**
	 * 
	 * Opens the findAvailableTableFrame to create a new booking with a date and a number of guests.
	 */
	@Override
	public void createBooking() {
		next(new FindAvailableTableFrame(bookingCtrl, this));
	}

	/**
	 * Opens the FindAvailableBoardGameFrame to add BoardGameCopy to the booking.
	 * 
	 */
	@Override
	public void addBoardGameCopy() {
		next(new FindAvailableBoardGameFrame(bookingCtrl, bgCopyCtrl, this));
	}
	
	/**
	 * Opens the AddCustomerFrame to add a customer to the booking.
	 */
	@Override
	public void addCustomerDetails() {
		next(new AddCustomerFrame(bookingCtrl, this));
	}

	/**
	 * Opens the OverviewFrame to see the entire booking including the price and making it possible to complete
	 * or cancel the booking.
	 */
	@Override
	public void completeBooking() {
		next(new OverviewFrame(bookingCtrl, customerDao, membershipDao, this));
	}
	
	/**
	 * Clears the frame stack closing all windows, and making the main visible again.
	 * 
	 */
	public void returnToMain() {
		clearFrameStack();
		setVisible(true);
	}
	
	/**
	 * Clears the frame stack, making sure first call close on all frames in it which should dispose of them properly.
	 * 
	 */
	public void clearFrameStack() {
		for (FrameIF frame : frameStack) {
			frame.close();
		}
		frameStack.clear();
	}
	
	/**
	 * Used to add a frame on top of the frame stack. Will make sure to exit the prior top of the stack should it exist calls enter on the newly opened one.
	 * 
	 * @param nextFrame The frame that should be shown next.
	 */
	public void next(FrameIF nextFrame) {
		this.setVisible(false);
		if (frameStack.size() > 0) {
			frameStack.peek().exit();
		}
		
		frameStack.push(nextFrame);
		nextFrame.enter();
	}
	
	/**
	 * Closes the current top of the framestack, removing and enters the frame underneath it if it exists. Otherwise it returns to main.
	 * 
	 */
	public void back() {
		if (frameStack.size() > 1) {
			frameStack.pop().close();
			frameStack.peek().enter();
		} else if (frameStack.size() == 1) {
			returnToMain();
		}
		
	}
	
	/**
	 * Opens the CreateBoardGame frame to add new boardgames to the database alongside its copies.
	 */
	public void crudBoardGame() {
		next(new CreateBoardGame(bgCopyDao, bookingDao, tableDao, customerDao, membershipDao, boardGameDao, this));
	}

}
