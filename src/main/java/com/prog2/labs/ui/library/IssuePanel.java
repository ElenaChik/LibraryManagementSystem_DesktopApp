package com.prog2.labs.ui.library;

import java.awt.Button;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.app.commons.Colors;
import com.prog2.labs.i18n.LibraryLocale;
import com.prog2.labs.i18n.Localizable;
import com.prog2.labs.model.FormResponse;
import com.prog2.labs.model.Status;
import com.prog2.labs.service.LibraryService;
import com.prog2.labs.ui.components.CustomTable;

import static com.prog2.labs.app.commons.Constants.EMPTY;

/**
 * The IssuePanel class.
 * 		extends JPanel implements Localizable
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class IssuePanel extends JPanel implements Localizable {

	private static final long serialVersionUID = 1L;
	private static String COLUMN_AVAILABLE_BOOK_CATALOG[] = {"ID","ISBN","Title","Author", "Ppublisher"};
	private static String COLUMN_USER_CATALOG[] = {"ID","NAME","CONTACT"};

	private LibraryService service;
	private LibraryLocale locale;
	
	private String bookId;
	private String userId;
	
	private String isbnFilter;
	private String titleFilter;
	private String authorFilter;
	private String publisherFilter;
	
	private String userIdFilter;
	private String nameFilter;
	private String contactFilter;

	JLabel labelUserId;
	JTextField textUserId;
	JLabel labelName;
	JTextField textName;
	JLabel labelContact;
	JTextField textContact;
	Button buttonUsersFilter;

	CustomTable tableBooksAvailable;
	DefaultTableModel modelBooks;
	JScrollPane spBooks;
	
	JLabel labelBooksCatalog; 
	JLabel labelIsbn;
	JLabel labelTitle;
	JLabel labelAuthor;
	JLabel labelPublisher;
	JTextField textIsbn;
	JTextField textTitle;
	JTextField textAuthor;
	JTextField textPublisher;
	Button buttonBooksFilter;

	JLabel labelUserCatalog; 
	JLabel lableUsers;
	CustomTable tableUsers;
	DefaultTableModel modelUsers;
	JScrollPane spUsers;
	Button buttonResetUserFilter;

	Button buttonIssueBook;
	JLabel labelMessage;
	Button buttonResetFilter;

	IssuePanel(LibraryService service, LibraryManager manager) {
		this.service = service;
		this.locale = manager.getLocale();
		setLayout(null);
		
		init();
	}

	public void init() {
		// Create Books Block
		labelBooksCatalog = new JLabel("");
		labelBooksCatalog.setBounds(20, 30, 450, 20);
		add(labelBooksCatalog);

		createFilterBooksButton();
		createResetFilterButton();
		createBookFilterBlock();
		createBooksAvailableTableBlock(service.getArrayOfAvailableBooks(isbnFilter, 
				titleFilter, authorFilter, publisherFilter));
		
		
		// Create Users Block
		labelUserCatalog = new JLabel("");
		labelUserCatalog.setBounds(500, 30, 450, 20);
		add(labelUserCatalog);
		
		createUserFilterBlock();
		createFilterUserButton();
		createResetUserFilterButton();
		createUserTableBlock(service.getArrayOfUsers(userIdFilter, nameFilter, contactFilter));

		//final Block
		createIssueButton();
		addErrorMessageLabel();
		setVisible(true);
	}

	/**
	 * Method Create Books Available Table Block
	 * based on JTable element
	 * 
	 * @param arrDepartments String[][]
	 */
	private void createBooksAvailableTableBlock(FormResponse<String[][]> availableDataResponse) { 

		String[][] availableData = null;
		if (availableDataResponse.getStatus().equals(Status.DONE)) {
			availableData = availableDataResponse.getValue();
		}
		
		modelBooks = new DefaultTableModel(availableData, COLUMN_AVAILABLE_BOOK_CATALOG); 

		tableBooksAvailable = new CustomTable(modelBooks, Colors.LightYellow); 
		tableBooksAvailable.setBounds(200,40,200,200);
		tableBooksAvailable.setAutoCreateRowSorter(true);


		JScrollPane spBooks = new JScrollPane(tableBooksAvailable);
		spBooks.setBounds(20, 210, 420, 200);
		add(spBooks);

        /*
         * Listener for JTable table Books
         */
        tableBooksAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e) {

        		int row = tableBooksAvailable.rowAtPoint(e.getPoint());
        		setActiveBookID(tableBooksAvailable.getValueAt(row, 0).toString());
        	}
        });
	}
	
	// Create filterBooks Block
	private void createBookFilterBlock() {
		
		labelIsbn = new JLabel("");
		labelIsbn.setBounds(20,80, 80,20);
		add(labelIsbn);
		
		labelTitle = new JLabel("");
		labelTitle.setBounds(20,100, 80,20);
		add(labelTitle);
		
		labelAuthor = new JLabel("");
		labelAuthor.setBounds(20,120, 80,20);
		add(labelAuthor);
		
		labelPublisher = new JLabel("");
		labelPublisher.setBounds(20,140, 85,20);
		add(labelPublisher);
	

		textIsbn = new JTextField();
		textIsbn.setBounds(150, 80, 150, 20);
		add(textIsbn);
		
		textTitle = new JTextField();
		textTitle.setBounds(150, 100, 150, 20);
		add(textTitle);
		
		textAuthor = new JTextField();
		textAuthor.setBounds(150, 120, 150, 20);
		add(textAuthor);
		
		textPublisher = new JTextField();
		textPublisher.setBounds(150, 140, 150, 20);
		add(textPublisher);

		initFilter();
	}
	
	private void createFilterBooksButton() {

		buttonBooksFilter = new Button("");
		buttonBooksFilter.setActionCommand("filterBooks");
		buttonBooksFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setFilter();
				updateAvailableBooksTable();
			}
		});
		buttonBooksFilter.setBackground(Color.LIGHT_GRAY);
		buttonBooksFilter.setForeground(Color.BLACK);
		buttonBooksFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonBooksFilter.setBounds(20, 180, 120, 20);
		add(buttonBooksFilter);
		setVisible(true); 
		
	}
	
	private void createResetFilterButton() {

		buttonResetFilter = new Button("");
		buttonResetFilter.setActionCommand("resetFilter");
		buttonResetFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				initFilter();
				setEmptyFilterFields();
				updateAvailableBooksTable();
				setActiveBookID(EMPTY);
			}
		});
		buttonResetFilter.setBackground(Color.LIGHT_GRAY);
		buttonResetFilter.setForeground(Color.BLACK);
		buttonResetFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonResetFilter.setBounds(160, 180, 120, 20);
		add(buttonResetFilter);
	}

	private void createUserFilterBlock() {
		labelUserId = new JLabel("");
		labelUserId.setBounds(500,80, 120,20);
		add(labelUserId);

		textUserId = new JTextField();
		textUserId.setBounds(650, 80, 150, 20);
		add(textUserId);
	
		labelName = new JLabel("");
		labelName.setBounds(500,100, 80,20);
		add(labelName);


		textName = new JTextField();
		textName.setBounds(650,100, 150, 20);
		add(textName);
	
		labelContact = new JLabel("");
		labelContact.setBounds(500,120, 80,20);
		add(labelContact);

		textContact = new JTextField();
		textContact.setBounds(650, 120, 150, 20);
		add(textContact);

		initUserFilter();
	}
	
	private void createResetUserFilterButton() {

		buttonResetUserFilter = new Button("Reset Filter");
		buttonResetUserFilter.setActionCommand("Reset");
		buttonResetUserFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetUserFilterFields();
				initUserFilter();
				updateUserTable();
				setActiveUserID(EMPTY);
			}
		});
		buttonResetUserFilter.setBackground(Color.LIGHT_GRAY);
		buttonResetUserFilter.setForeground(Color.BLACK);
		buttonResetUserFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonResetUserFilter.setBounds(640, 180, 120, 20);
		add(buttonResetUserFilter);
	}

	/**
	 * Method Create User Table
	 * based on JTable element
	 * 
	 * @param arr String[][]
	 */
	private void createUserTableBlock(FormResponse<String[][]> formResponseUsers) { 

		String[][] userData = null;
		if(formResponseUsers.getStatus().equals(Status.DONE)) {
			userData = formResponseUsers.getValue();
		}

		DefaultTableModel modelUsers = new DefaultTableModel(userData, COLUMN_USER_CATALOG); 

		tableUsers = new CustomTable(modelUsers, Color.lightGray);
		tableUsers.setBounds(200,40,200,200);
		JScrollPane spUsers = new JScrollPane(tableUsers);
		spUsers.setBounds(500, 210, 420, 200);

		add(spUsers);

		/*
		* Listener for JTable table 
		*/
		tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {

			int row = tableUsers.rowAtPoint(e.getPoint());
			setActiveUserID(tableUsers.getValueAt(row, 0).toString());
		}
		});
	}

	private void createFilterUserButton() {

		buttonUsersFilter = new Button("");
		buttonUsersFilter.setActionCommand("filter");
		buttonUsersFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setUserFilter();
				updateUserTable();
			}
		});
		buttonUsersFilter.setBackground(Color.LIGHT_GRAY);
		buttonUsersFilter.setForeground(Color.BLACK);
		buttonUsersFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonUsersFilter.setBounds(500, 180, 120, 20);
		add(buttonUsersFilter);
		setVisible(true); 
		
	}
	
	private void createIssueButton() {

		buttonIssueBook = new Button("");
		buttonIssueBook.setActionCommand("Issue");
		buttonIssueBook.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			//Check bookId and userId is not empty
			if (bookId.isEmpty() || userId.isEmpty()) {
				setErrorMessage("ChooseBookAndUser");
			} else {
				FormResponse<String> issueBookResponse = service.issueBook(bookId, userId);
				switch (issueBookResponse.getStatus()) {
				case DONE : setEmptyFilterFields();
							resetUserFilterFields();

							setActiveBookID(EMPTY);
							setActiveUserID(EMPTY);
							setErrorMessage(EMPTY);

							initFilter();
							initUserFilter();

							updateAvailableBooksTable();
							updateUserTable();
					break;
				case ERROR : setErrorMessage("Error" + issueBookResponse.getMessage());
					break;
				}
			}
		}
		});
		buttonIssueBook.setBackground(Color.LIGHT_GRAY);
		buttonIssueBook.setForeground(Color.BLACK);
		buttonIssueBook.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonIssueBook.setBounds(20, 500, 150, 25);
		add(buttonIssueBook);
		setVisible(true); 

		setActiveBookID(EMPTY);
		setActiveUserID(EMPTY);
	}

	private void setActiveBookID(String activeBookId) {
		this.bookId = activeBookId;
	}

	private void setActiveUserID(String activeUserId) {
		this.userId = activeUserId;
	}

	private void addErrorMessageLabel() {
		labelMessage = new JLabel("message");
		labelMessage.setBounds(20, 450, 340, 20);
		labelMessage.setVisible(true);
		labelMessage.setText("");
		labelMessage.setForeground(Color.red);
		add(labelMessage);
	}

	private void initFilter() {
		this.isbnFilter = EMPTY;
		this.titleFilter = EMPTY;
		this.authorFilter = EMPTY;
		this.publisherFilter = EMPTY;
	}

	private void setEmptyFilterFields() {
		textIsbn.setText(EMPTY);
		textTitle.setText(EMPTY);
		textAuthor.setText(EMPTY);
		textPublisher.setText(EMPTY);
	}

	private void setFilter() {
		this.isbnFilter = textIsbn.getText();
		this.titleFilter = textTitle.getText();
		this.authorFilter = textAuthor.getText();
		this.publisherFilter = textPublisher.getText();
	}

	private void updateAvailableBooksTable() {
		String[][] catalogData = null;
		
		FormResponse<String[][]> catalogDataResponse 
				= service.getArrayOfAvailableBooks(isbnFilter, titleFilter, authorFilter, publisherFilter);

		if (catalogDataResponse.getStatus().equals(Status.DONE)) {
			catalogData = catalogDataResponse.getValue();
		} 

		tableBooksAvailable.setModel(
			new DefaultTableModel(catalogData, COLUMN_AVAILABLE_BOOK_CATALOG)
		);
	}

	private void updateUserTable() {
		String[][] catalogData = null;
		
		FormResponse<String[][]> userDataResponse 
				= service.getArrayOfUsers(userIdFilter, nameFilter, contactFilter);

		if (userDataResponse.getStatus().equals(Status.DONE)) {
			catalogData = userDataResponse.getValue();
		} 

		tableUsers.setModel(
			new DefaultTableModel(catalogData, COLUMN_USER_CATALOG)
		);
	}
	private void setErrorMessage(String message) {
		labelMessage.setText(message);
	}
	
	private void initUserFilter() {
		this.userIdFilter = EMPTY;
		this.nameFilter = EMPTY;
		this.contactFilter = EMPTY;
	}
	
	private void resetUserFilterFields() {
		textUserId.setText(EMPTY);
		textName.setText(EMPTY);
		textContact.setText(EMPTY);
	}

	private void setUserFilter() {
		this.userIdFilter = textUserId.getText();
		this.nameFilter = textName.getText();
		this.contactFilter = textContact.getText();
	}
	
	public void initLocalaizedText() {
		
		labelBooksCatalog.setText(locale.get("availableBooksCatalog"));
		
		labelUserCatalog.setText(locale.get("usersCatalog"));
		
		COLUMN_AVAILABLE_BOOK_CATALOG = new String[]{"ID",
													"ISBN",
													locale.get("title"),
													locale.get("author"), 
													locale.get("publisher")};
		COLUMN_USER_CATALOG = new String[] {"ID",
											locale.get("name"),
											locale.get("contact")};

		

		labelIsbn.setText(locale.get("byISBN"));
		labelTitle.setText(locale.get("byTitle"));
		labelAuthor.setText(locale.get("byAuthor"));
		labelPublisher.setText(locale.get("byPublisher"));

		labelUserId.setText(locale.get("byUserId"));
		labelName.setText(locale.get("byName"));
		labelContact.setText(locale.get("byContact"));

		buttonBooksFilter.setLabel(locale.get("filterBooks"));
		buttonResetFilter.setLabel(locale.get("resetFilter"));
		buttonUsersFilter.setLabel(locale.get("filterUsers"));
		buttonResetUserFilter.setLabel(locale.get("resetFilter"));
		buttonIssueBook.setLabel(locale.get("issueBook"));
	}
	
}
