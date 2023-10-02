package com.prog2.labs.ui.library;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.db.entity.User.Role;
import com.prog2.labs.i18n.Localizable;
import com.prog2.labs.model.FormResponse;
import com.prog2.labs.model.Status;
import com.prog2.labs.service.LibraryService;

import static com.prog2.labs.app.commons.Constants.EMPTY;

/**
 * The ManageLibraryPanel class.
 * 		extends JPanel implements Localizable
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class ManageLibraryPanel extends JPanel implements Localizable {

	private static final long serialVersionUID = 1L;

	private LibraryService service;

	String userRole;
	
	JLabel lableBooksAvailable;
	JTable tableBooksAvailable;
	DefaultTableModel modelBooks;
	JScrollPane spBooks;
	
	JLabel labelBooksCatalog; 
	JLabel labelIsbn;
	JLabel labelTitle;
	JLabel labelAuthor;
	JLabel labelPublisher;
	JLabel labelPrice;
	JLabel labelQuantity;
	JTextField textPrice;
	JTextField textQuantity;
	JTextField textIsbn;
	JTextField textTitle;
	JTextField textAuthor;
	JTextField textPublisher;
	
	JLabel labelUserName;
	JTextField textUserName;
	JLabel labelUserRole;
	JList listUserRole;
	JLabel labelUserLogin;
	JTextField textUserLogin;
	JLabel labelUserPassword;
	JTextField textUserPassword;
	
	Button buttonAddBook;
	
	JLabel labelUsers;
	JLabel labelUserId;
	JTextField textUserId;
	JLabel labelUserContact;
	JTextField textUserContact;
	Button buttonAddUser;
	JLabel labelMessage;
	
	
	ManageLibraryPanel(LibraryService service, LibraryManager manager) {
		this.service = service;
		setLayout(null);

		labelBooksCatalog = new JLabel("ADD NEW BOOK");
		labelBooksCatalog.setBounds(20, 30, 450, 20);
		add(labelBooksCatalog);

		createAddBookBlock();
		createAddBooksButton();

		labelUsers = new JLabel("ADD NEW USER");
		labelUsers.setBounds(500, 30, 450, 20);
		add(labelUsers);
		
		createAddUserBlock();
		createAddUserButton();

		addErrorMessageLabel();
	}

	// Create new Book Block
		private void createAddBookBlock() {

			labelIsbn = new JLabel("ISBN: ");
			labelIsbn.setBounds(20,80, 80,20);
			add(labelIsbn);

			labelTitle = new JLabel("Title: ");
			labelTitle.setBounds(20,100, 80,20);
			add(labelTitle);

			labelAuthor = new JLabel("Author: ");
			labelAuthor.setBounds(20,120, 80,20);
			add(labelAuthor);

			labelPublisher = new JLabel("Publisher: ");
			labelPublisher.setBounds(20,140, 85,20);
			add(labelPublisher);
			
			labelPrice = new JLabel("Price: ");
			labelPrice.setBounds(20,160, 85,20);
			add(labelPrice);
			
			labelQuantity = new JLabel("Quantity: ");
			labelQuantity.setBounds(20,180, 85,20);
			add(labelQuantity);

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
			
			textPrice = new JTextField();
			textPrice.setBounds(150, 160, 150, 20);
			add(textPrice);
			
			textQuantity = new JTextField();
			textQuantity.setBounds(150, 180, 150, 20);
			add(textQuantity);
		}

		private void createAddBooksButton() {

			buttonAddBook = new Button("ADD BOOK");
			buttonAddBook.setActionCommand("Add Book");
			buttonAddBook.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					FormResponse<String> response = service.addBook(textIsbn.getText(), 
							textTitle.getText(), textAuthor.getText(), textPublisher.getText(), 
							textPrice.getText(), textQuantity.getText());
					
					if(response.getStatus().equals(Status.ERROR)) {
						setErrorMessage(response.getMessage());
					} else {
						setErrorMessage(EMPTY);
						emptyAddBookForm();
					}
				}
			});
			buttonAddBook.setBackground(Color.LIGHT_GRAY);
			buttonAddBook.setForeground(Color.BLACK);
			buttonAddBook.setFont(new Font("Dialog", Font.BOLD, 11));
			buttonAddBook.setBounds(20, 250, 80, 20);
			add(buttonAddBook);
			setVisible(true); 
			
		}

		/**
		 * Method Create User Table
		 * based on JTable element
		 * 
		 * @param arrTeachers String[][]
		 */
	private void createAddUserBlock() { //String[][] arrUsers

		labelUserName = new JLabel("User Name: ");
		labelUserName.setBounds(500,80, 80,20);
		add(labelUserName);

			textUserName = new JTextField();
		textUserName.setBounds(650, 80, 150, 20);
		add(textUserName);

		labelUserRole = new JLabel("User Role: ");
		labelUserRole.setBounds (500, 160, 80,20);
		add(labelUserRole);

		String[] role = service.enumRoleToStringArray();

		listUserRole = new JList(role);
			listUserRole.setBounds(650, 160, 150, 40);
		listUserRole.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUserRole.setSelectedIndex(0);
		add(listUserRole);

		listUserRole.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
				    setUserRole(listUserRole.getSelectedValue().toString());
				}
			}
		});

		labelUserLogin = new JLabel("User Login: ");
		labelUserLogin.setBounds(500, 120, 80, 20);
		add(labelUserLogin);

		textUserLogin = new JTextField();
		textUserLogin.setBounds(650, 120, 150, 20);
		add(textUserLogin);

		labelUserPassword = new JLabel("Password: ");
		labelUserPassword.setBounds(500,140, 80,20);
		add(labelUserPassword);

		textUserPassword = new JTextField();
		textUserPassword.setBounds(650, 140, 150, 20);
		add(textUserPassword);

		labelUserContact = new JLabel("Contact: ");
		labelUserContact.setBounds (500,100, 80,20);
		add(labelUserContact);

		textUserContact = new JTextField();
		textUserContact.setBounds (650, 100, 150, 20);
		add(textUserContact);
		
	}

	private void createAddUserButton() {

		buttonAddUser = new Button("ADD USER");
		buttonAddUser.setActionCommand("Add User");
		buttonAddUser.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			FormResponse<String> response = service.addUser(
							textUserName.getText(), stringToRole(getUserRole()), 
							textUserLogin.getText(), textUserPassword.getText(), 
							textUserContact.getText());

			switch(response.getStatus()) {
				case ERROR : setErrorMessage(response.getMessage());
					break;
				case DONE : setErrorMessage(EMPTY); emptyAddUserForm();
					break;
				}
			}
		});
		buttonAddUser.setBackground(Color.LIGHT_GRAY);
		buttonAddUser.setForeground(Color.BLACK);
		buttonAddUser.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonAddUser.setBounds(500, 250, 80, 20);
		add(buttonAddUser);
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

	private void setErrorMessage(String message) {
		labelMessage.setText(message);
	}

	private void emptyAddBookForm() {
		textIsbn.setText(EMPTY); 
		textTitle.setText(EMPTY);
		textAuthor.setText(EMPTY); 
		textPublisher.setText(EMPTY); 
		textPrice.setText(EMPTY); 
		textQuantity.setText(EMPTY);
	}
		
	public String getUserRole() {
		return listUserRole.getSelectedValue().toString();
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Role stringToRole(String role) {
		return Role.valueOf(role);
	}
	
	private void emptyAddUserForm() {
		textUserName.setText(EMPTY); 
		textUserLogin.setText(EMPTY); 
		textUserPassword.setText(EMPTY);
		textUserContact.setText(EMPTY);
	}
	
	public void initLocalaizedText() {
	
	}
}
