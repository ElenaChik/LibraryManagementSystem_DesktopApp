package com.prog2.labs.ui.library;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.app.commons.Colors;
import com.prog2.labs.i18n.Localizable;
import com.prog2.labs.model.FormResponse;
import com.prog2.labs.model.Status;
import com.prog2.labs.service.LibraryService;
import com.prog2.labs.ui.components.CustomTable;

import static com.prog2.labs.app.commons.Constants.EMPTY;

/**
 * The ReturnPanel class.
 * 		extends JPanel implements Localizable
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class ReturnPanel extends JPanel implements Localizable {

	private static final String COLUMN_BOOK_TO_RETURN[] 
			= {"ISSUED_ID", "ISBN", "Title", "Author","Publisher", "UserId", "Name", "Contact", "Date Issued"};
	private LibraryService service;
	
	private String issuedId;

	JLabel labelBooksToReturn;
	CustomTable tableBooksToReturn;
	DefaultTableModel modelBooksToReturn;
	JScrollPane spBooksToReturn;
	Button buttonReturnBook;
	JLabel labelMessage;

	ReturnPanel(LibraryService service, LibraryManager manager) {
		this.service = service;
		init();
	}
	
	private void init() {
		setLayout(null);

		labelBooksToReturn = new JLabel("ISSUED BOOKS");
		labelBooksToReturn.setBounds(20, 30, 450, 20);
		add(labelBooksToReturn);

		createBooksToReturnTable(service.getArrayToReturnBooks());
		createReturnButton();
		addErrorMessageLabel();
		setVisible(true);
	}

	private void createBooksToReturnTable(FormResponse<String[][]> toReturnDataResponse) {

		String[][] toReturnData = null;
		
		if (toReturnDataResponse.getStatus().equals(Status.DONE)) {
			toReturnData = toReturnDataResponse.getValue();
		}
		modelBooksToReturn = new DefaultTableModel(toReturnData, COLUMN_BOOK_TO_RETURN); 
		
		tableBooksToReturn = new CustomTable(modelBooksToReturn, Colors.LightRed); 
		tableBooksToReturn.setAutoCreateRowSorter(true);
		tableBooksToReturn.setBounds(200,40,200,200);

		setTableColumnInvisible(0);

		JScrollPane spBooksToReturn = new JScrollPane(tableBooksToReturn);
		spBooksToReturn.setBounds(20, 80, 900, 400);
		add(spBooksToReturn);

        /*
         * Listener for JTable table Books
         */
		tableBooksToReturn.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e) {
        		int row = tableBooksToReturn.rowAtPoint(e.getPoint());
        		setActiveIssuedId(tableBooksToReturn.getValueAt(row, 0).toString());
        	}
		});
	}

	private void createReturnButton() {

		buttonReturnBook = new Button("RETURN BOOK");
		buttonReturnBook.setActionCommand("Return");
		buttonReturnBook.addMouseListener(new MouseAdapter() {
		/*
		 * Update Issued to Returned
		 */	
			@Override
			public void mouseClicked(MouseEvent e) {
				if (issuedId.isEmpty()) {
					setErrorMessage("Please choose a book");
				} else {
					FormResponse<String> response = service.updateIssuedToReturned(issuedId);
					switch (response.getStatus()) {
						case ERROR : setErrorMessage(response.getMessage());
							break;
						case DONE : updateIssuedTable();
									setActiveIssuedId(EMPTY);
							break;
					}
				}
				updateIssuedTable();
			}
		});
		buttonReturnBook.setBackground(Color.LIGHT_GRAY);
		buttonReturnBook.setForeground(Color.BLACK);
		buttonReturnBook.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonReturnBook.setBounds(20, 500, 150, 20);
		add(buttonReturnBook);
		
		setActiveIssuedId(EMPTY);
	}
	
	private void setActiveIssuedId(String issuedId) {
		this.issuedId = issuedId;
	}

	private void addErrorMessageLabel() {
		labelMessage = new JLabel("Message");
		labelMessage.setBounds(20, 450, 340, 20);
		labelMessage.setVisible(true);
		labelMessage.setText("");
		labelMessage.setForeground(Color.red);
		add(labelMessage);
	}
	
	private void setErrorMessage(String message) {
		labelMessage.setText(message);
	}
	
	private void updateIssuedTable() {
		String[][] issuedData = null;
		
		FormResponse<String[][]> issuedDataResponse 
				= service.getArrayToReturnBooks();

		if (issuedDataResponse.getStatus().equals(Status.DONE)) {
			issuedData = issuedDataResponse.getValue();
		} 

		tableBooksToReturn.setModel(
			new DefaultTableModel(issuedData, COLUMN_BOOK_TO_RETURN)
		);

		setTableColumnInvisible(0);
	}
	
	private void setTableColumnInvisible(int index) {
		tableBooksToReturn.getColumnModel().getColumn(index).setWidth(0);
		tableBooksToReturn.getColumnModel().getColumn(index).setMinWidth(0);
		tableBooksToReturn.getColumnModel().getColumn(index).setMaxWidth(0);
	}
	
	public void initLocalaizedText() {
	
	}
}

