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
import com.prog2.labs.model.SessionType;
import com.prog2.labs.model.Status;
import com.prog2.labs.service.LibraryService;
import com.prog2.labs.ui.components.CustomTable;

import static com.prog2.labs.app.commons.Constants.EMPTY;

/**
 * The CatalogPanel class.
 * 		extends JPanel implements Localizable
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class CatalogPanel extends JPanel implements Localizable {

	private static final long serialVersionUID = 1L;

	private static String COLUMN_BOOK_CATALOG[]
			= {"ID","ISBN","Title","Author", "Ppublisher", "Price", "Quantity"};

	private LibraryService service;
	private LibraryManager manager;
	private LibraryLocale locale;

	private String isbnFilter;
	private String titleFilter;
	private String authorFilter;
	private String publisherFilter;

	private String bookId;
	private String bookQuantity;

	//private JLabel lableBooksCatalog;
	private CustomTable tableBooksCatalog;
	private DefaultTableModel modelBooks;
	//private JScrollPane spBooks;
	
	private JLabel labelBooksCatalog; 
	private JLabel labelIsbn;
	private JLabel labelTitle;
	private JLabel labelAuthor;
	private JLabel labelPublisher;
	private JTextField textIsbn;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textPublisher;
	private Button buttonBooksFilter;
	private Button buttonResetFilter;

	private JLabel labelBookUpdate;
	private JTextField textBookUpdate;
	private Button buttonUpdate;
	private JLabel labelMessage;

	/**
	 * Constructor for Catalog panel Librarian
	 * @param service
	 * @param manager
	 */
	public CatalogPanel(LibraryService service, LibraryManager manager) {
		this.service = service;
		this.manager = manager;
		this.locale = manager.getLocale();

		init();
	}
	
	private void init() {
		setLayout(null);

		// Label Book Catalog
		labelBooksCatalog = new JLabel("BOOKS CATALOG");
		labelBooksCatalog.setBounds(20, 30, 450, 20);
		add(labelBooksCatalog);

		// Filter Block
		createBookFilterBlock();
		createFilterBooksButton();
		createResetFilterButton();

		createBooksCatalogTable(service.getArrayOfAllBooks(isbnFilter, 
				titleFilter, authorFilter, publisherFilter));
		
		// UpdateBook Block. Available only for Librarian
		if (manager.getSession().equals(SessionType.LIBRARY)) {
			updateBlock();

			createUpdateButton();
			addErrorMessageLabel();
		}
		setVisible(true);
	}
	
	@Override
	public void initLocalaizedText() {
		
		labelBooksCatalog.setText(locale.get("booksCatalog"));
		
		COLUMN_BOOK_CATALOG = new String[]{"ID",
											"ISBN",
											locale.get("title"),
											locale.get("author"), 
											locale.get("publisher"), 
											locale.get("price") + " "
													+ LibraryLocale.getActiveLanguage().getCurrency(), 
											locale.get("quantity")};
		updateBookCatalogTable();

		labelIsbn.setText(locale.get("byISBN"));
		labelTitle.setText(locale.get("byTitle"));
		labelAuthor.setText(locale.get("byAuthor"));
		labelPublisher.setText(locale.get("byPublisher"));

		buttonBooksFilter.setLabel(locale.get("filterBooks"));
		buttonResetFilter.setLabel(locale.get("resetFilter"));
	}

	private void createBooksCatalogTable(FormResponse<String[][]> catalogDataResponse) {

		String[][] catalogData = null;
		if (catalogDataResponse.getStatus().equals(Status.DONE)) {
			catalogData = catalogDataResponse.getValue();
		}

		modelBooks = new DefaultTableModel(catalogData, COLUMN_BOOK_CATALOG);

		tableBooksCatalog = new CustomTable(modelBooks, Colors.LightOrange); 
		tableBooksCatalog.setAutoCreateRowSorter(true);

		JScrollPane spBooksCatalog = new JScrollPane(tableBooksCatalog);
		spBooksCatalog.setBounds(20, 210, 900, 200);
		tableBooksCatalog.setTableColumnInvisible(0);
		add(spBooksCatalog);

        /*
         * Listener for JTable table Books
         */
		tableBooksCatalog.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e) {

        		int row = tableBooksCatalog.rowAtPoint(e.getPoint());
        		setActiveBookInfo(
        				tableBooksCatalog.getValueAt(row, 0).toString(),
        				tableBooksCatalog.getValueAt(row, 6).toString()
        				);
        	}
        });
	}
	
	// Create filterBooks Block
	private void createBookFilterBlock() {
		
		labelIsbn = new JLabel();
		labelIsbn.setBounds(20,80, 80,20);
		add(labelIsbn);
		
		labelTitle = new JLabel();
		labelTitle.setBounds(20,100, 80,20);
		add(labelTitle);
		
		labelAuthor = new JLabel();
		labelAuthor.setBounds(20,120, 80,20);
		add(labelAuthor);
		
		labelPublisher = new JLabel();
		labelPublisher.setBounds(20,140, 85, 20);
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

		buttonBooksFilter = new Button("Filter Books");
		buttonBooksFilter.setActionCommand("Filter");
		buttonBooksFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setFilter();
				updateBookCatalogTable();
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
				updateBookCatalogTable();
				resetActiveBookInfo();
			}
		});
		buttonResetFilter.setBackground(Color.LIGHT_GRAY);
		buttonResetFilter.setForeground(Color.BLACK);
		buttonResetFilter.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonResetFilter.setBounds(160, 180, 120, 20);
		add(buttonResetFilter);
	}

	private void updateBlock() {
		labelBookUpdate = new JLabel("Update quantity: ");
		labelBookUpdate.setBounds(20,430, 100, 20);
		add(labelBookUpdate);
	

		textBookUpdate = new JTextField();
		textBookUpdate.setBounds(150, 430, 150, 20);
		add(textBookUpdate);
	}
	
	private void createUpdateButton() {

		buttonUpdate = new Button("UPDATE");
		buttonUpdate.setActionCommand("Update");
		buttonUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (textBookUpdate.getText().isEmpty())  {
					setErrorMessage("Fill in new quantity");
				} else if (Integer.valueOf(bookQuantity) >= Integer.valueOf(textBookUpdate.getText())) {
					setErrorMessage("Quantity is not valid");
				} else {
					FormResponse<String> response = service.updateBookQuantity(bookId, textBookUpdate.getText());
					switch (response.getStatus()) {
						case DONE : updateBookCatalogTable();
									textBookUpdate.setText(EMPTY);
									setErrorMessage(EMPTY);
									resetActiveBookInfo();
							break;
						case ERROR : setErrorMessage(response.getMessage());
							break;
					}
				}
			}
		});

		buttonUpdate.setBackground(Color.LIGHT_GRAY);
		buttonUpdate.setForeground(Color.BLACK);
		buttonUpdate.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonUpdate.setBounds(20, 500, 80, 20);
		add(buttonUpdate);
		setVisible(true); 
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

	private void updateBookCatalogTable() {
		String[][] catalogData = null;
		
		FormResponse<String[][]> catalogDataResponse 
				= service.getArrayOfAllBooks(isbnFilter, titleFilter, authorFilter, publisherFilter);

		if (catalogDataResponse.getStatus().equals(Status.DONE)) {
			catalogData = catalogDataResponse.getValue();
		} 

		tableBooksCatalog.setModel(
			new DefaultTableModel(catalogData, COLUMN_BOOK_CATALOG)
		);
		tableBooksCatalog.setTableColumnInvisible(0);
	}
	
	private void setActiveBookInfo(String bookId, String bookQuantity) {
		this.bookId = bookId;
		this.bookQuantity = bookQuantity;
	}
	
	private void resetActiveBookInfo() {
		this.bookId = EMPTY;
		this.bookQuantity = EMPTY;
	}

	private void setErrorMessage(String message) {
		labelMessage.setText(message);
	}
}
