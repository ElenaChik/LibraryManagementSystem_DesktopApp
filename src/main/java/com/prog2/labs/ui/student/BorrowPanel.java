package com.prog2.labs.ui.student;

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
import com.prog2.labs.service.StudentService;
import com.prog2.labs.ui.components.CustomTable;

import static com.prog2.labs.app.commons.Constants.EMPTY;

/**
 * The BorrowPanel class.
 * 		extends JPanel implements Localizable
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class BorrowPanel extends JPanel implements Localizable {

	private static final long serialVersionUID = 1L;
	private static String COLUMN_AVAILABLE_BOOK_CATALOG[]
			= {"ID","ISBN","Title","Author", "Publisher"};
	private static String COLUMN_BOOK_TO_RETURN[]
			= {"ISSUED_ID", "ISBN", "Title", "Author", "Date Issued"};
	
	private StudentService service;
	private LibraryManager manager;
	private LibraryLocale locale;
	
	private String bookId;
	private String userId;
	private String issuedId;
	
	private String isbnFilter;
	private String titleFilter;
	private String authorFilter;
	private String publisherFilter;
	
	
	JLabel lableBooksAvailable;
	JLabel labelIsbn;
	JLabel labelTitle;
	JLabel labelAuthor;
	JLabel labelPublisher;
	JTextField textIsbn;
	JTextField textTitle;
	JTextField textAuthor;
	JTextField textPublisher;
	Button buttonBooksFilter;
	Button buttonResetFilter;
	
	CustomTable tableBooksAvailable;
	DefaultTableModel modelBooks;
	JScrollPane spBooks;
	Button buttonBorrowBook;

	JLabel lableToReturn;
	CustomTable tableToReturn;
	DefaultTableModel modelToReturn;
	JScrollPane spToReturn;
	Button buttonReturnBook;
	JLabel labelMessage;

	BorrowPanel(StudentService service, LibraryManager manager) {
		this.service = service;
		this.manager = manager;
		this.locale = manager.getLocale();
		setLayout(null);
		
		setActiveUserID();
		
		init();
	}
		
	public void init() {
		// Create AVAILABLE Books Block
		lableBooksAvailable = new JLabel();
		lableBooksAvailable.setBounds(20, 30, 450, 20);
		add(lableBooksAvailable);

		//Book Filter Block
		createBookFilterBlock();
		createFilterBooksButton();
		createResetFilterButton();
		createBooksAvailableTableBlock(service.getArrayOfAvailableBooks(isbnFilter, 
				titleFilter, authorFilter, publisherFilter)); 
		createBorrowButton();
		
		// Create Books to RETURN
		lableToReturn = new JLabel();
		lableToReturn.setBounds(500, 30, 450, 20);
		add(lableToReturn);
		
		createToReturnTableBlock(service.getArrayOfBooksUserToReturn(userId));
		createReturnButton();
		
		// final Block
		addErrorMessageLabel();
		setVisible(true);
		initLocalaizedText();
	}
	
	@Override
	public void initLocalaizedText() {
		lableBooksAvailable.setText(locale.get("availableBooksCatalog"));
		lableToReturn.setText(locale.get("bookToReturn"));

		COLUMN_AVAILABLE_BOOK_CATALOG = new String[]{"ID",
													"ISBN",
													locale.get("title"),
													locale.get("author"), 
													locale.get("publisher")};
		updateAvailableBooksTable();

		COLUMN_BOOK_TO_RETURN = new String[]{locale.get("issuedId"),
											"ISBN", 
											locale.get("title"),
											locale.get("author"), 
											locale.get("dateIssued")};
		updateIssuedTable();
		
		labelIsbn.setText(locale.get("byISBN"));
		labelTitle.setText(locale.get("byTitle"));
		labelAuthor.setText(locale.get("byAuthor"));
		labelPublisher.setText(locale.get("byPublisher"));
		
		buttonBooksFilter.setLabel(locale.get("filterBooks"));
		buttonResetFilter.setLabel(locale.get("resetFilter"));
		buttonBorrowBook.setLabel(locale.get("borrowBook"));
		buttonReturnBook.setLabel(locale.get("returnBook"));
	}

	private void createBooksAvailableTableBlock(FormResponse<String[][]> availableDataResponce) { 

		String availableData[][] = null;
		if (availableDataResponce.getStatus().equals(Status.DONE)) {
			availableData = availableDataResponce.getValue();
		}

		modelBooks = new DefaultTableModel(availableData, COLUMN_AVAILABLE_BOOK_CATALOG); 
		tableBooksAvailable = new CustomTable(modelBooks, Colors.LightYellow);
		tableBooksAvailable.setBounds(200,40,200,200);
		tableBooksAvailable.setTableColumnInvisible(0);
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
		
		labelIsbn = new JLabel("by ISBN: ");
		labelIsbn.setBounds(20,80, 80,20);
		add(labelIsbn);
		
		labelTitle = new JLabel("by Title: ");
		labelTitle.setBounds(20,100, 80,20);
		add(labelTitle);
		
		labelAuthor = new JLabel("by Author: ");
		labelAuthor.setBounds(20,120, 80,20);
		add(labelAuthor);
		
		labelPublisher = new JLabel("by Publisher: ");
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

		buttonBooksFilter = new Button();
		buttonBooksFilter.setActionCommand("Filter");
		buttonBooksFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setFilter();
				updateAvailableBooksTable();
				setErrorMessage(EMPTY);
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

		buttonResetFilter = new Button("Reset Filter");
		buttonResetFilter.setActionCommand("Reset");
		buttonResetFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				initFilter();
				setEmptyFilterFields();
				updateAvailableBooksTable();
				setActiveBookID(EMPTY);
				setErrorMessage(EMPTY);
			}
		});
		buttonResetFilter.setBackground(Color.LIGHT_GRAY);
		buttonResetFilter.setForeground(Color.BLACK);
		buttonResetFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonResetFilter.setBounds(160, 180, 120, 20);
		add(buttonResetFilter);
	}
	
	private void createBorrowButton() {

		buttonBorrowBook = new Button("BORROW BOOK");
		buttonBorrowBook.setActionCommand("Issue");
		buttonBorrowBook.setBackground(Color.LIGHT_GRAY);
		buttonBorrowBook.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			//Check bookId and userId is not empty
			if (bookId == null || bookId.isEmpty()) {
				setErrorMessage("Choose Book");
			} else {
				FormResponse<String> issueBookResponse = service.borrowBook(bookId, userId);
				switch (issueBookResponse.getStatus()) {
					case DONE : setEmptyFilterFields();
								setActiveBookID(EMPTY);
								setErrorMessage(EMPTY);
								initFilter();
								updateAvailableBooksTable();
								updateIssuedTable();
						break;
					case ERROR : setErrorMessage("Error" + issueBookResponse.getMessage());
						break;
				}
			}
				
		}
		});
		buttonBorrowBook.setForeground(Color.BLACK);
		buttonBorrowBook.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonBorrowBook.setBounds(20, 480, 150, 25);
		add(buttonBorrowBook);
		setVisible(true); 
		
	}
	
	private void createToReturnTableBlock(FormResponse<String[][]> toReturnDataResponse) { 

		String[][] toReturn = null;
		if (toReturnDataResponse.getStatus().equals(Status.DONE)) {
			toReturn = toReturnDataResponse.getValue();
		}

		DefaultTableModel modelToReturn = new DefaultTableModel(toReturn, COLUMN_BOOK_TO_RETURN ); 

		tableToReturn = new CustomTable(modelToReturn, Colors.LightGreen);
		tableToReturn.setBounds(200,40,200,200);
		tableToReturn.setTableColumnInvisible(0);

		JScrollPane spToReturn = new JScrollPane(tableToReturn);
		spToReturn.setBounds(500, 210, 420, 200);

		add(spToReturn);
        
        /*
         * Listener for JTable table toReturn
         */	    
		tableToReturn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = tableToReturn.rowAtPoint(e.getPoint());

				setActiveIssuedId(tableToReturn.getValueAt(row, 0).toString());
			}
		});
	}

	private void createReturnButton() {

		buttonReturnBook = new Button("RETURN BOOK");
		buttonReturnBook.setActionCommand("Issue");
		buttonReturnBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (issuedId.isEmpty()) {
					setErrorMessage("Please choose a book");
				} else {
					FormResponse<String> response = service.updateIssuedToReturned(issuedId);
					switch (response.getStatus()) {
						case ERROR : setErrorMessage(response.getMessage());
							break;
						case DONE : setActiveIssuedId(EMPTY);
									updateAvailableBooksTable();
									setErrorMessage(EMPTY);
									updateIssuedTable();
							break;
					}
				}
			}
		});
		buttonReturnBook.setBackground(Color.LIGHT_GRAY);
		buttonReturnBook.setForeground(Color.BLACK);
		buttonReturnBook.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonReturnBook.setBounds(500, 480, 150, 25);
		add(buttonReturnBook);
		setVisible(true); 
		
		setActiveIssuedId(EMPTY);
	}

	private void setActiveBookID(String activeBookId) {
		this.bookId = activeBookId;
	}

	private void setActiveIssuedId(String activeIssuedId) {
		this.issuedId = activeIssuedId;
	}

	private void setActiveUserID() {
		this.userId = String.valueOf(manager.getUser().getUser_id());
	}

	private void addErrorMessageLabel() {
		labelMessage = new JLabel("Message");
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
		tableBooksAvailable.setTableColumnInvisible(0);
	}

	private void setErrorMessage(String message) {
		labelMessage.setText(message);
	}

	private void updateIssuedTable() {
		String[][] issuedData = null;
		
		FormResponse<String[][]> issuedDataResponse 
				= service.getArrayOfBooksUserToReturn(userId);

		if (issuedDataResponse.getStatus().equals(Status.DONE)) {
			issuedData = issuedDataResponse.getValue();
		} 

		tableToReturn.setModel(
			new DefaultTableModel(issuedData, COLUMN_BOOK_TO_RETURN)
		);
		tableToReturn.setTableColumnInvisible(0);
	}
}
