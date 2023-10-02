package com.prog2.labs.ui.login;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.i18n.Language;
import com.prog2.labs.model.FormResponse;
import com.prog2.labs.service.LoginService;

/**
 * The LoginFrame class.
 * 		extends JPanel
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Login";
	private LoginService service;
	private LibraryManager manager;

	JPanel panel;
	Button loginButton;
	JLabel logLabel;
	JTextField logText;
	JLabel passLabel;
	JPasswordField passField;
	JLabel labelMessage;
	JLabel testLabel;

	public LoginFrame(LibraryManager manager) {
		super(TITLE);
		this.manager = manager;
		this.service = new LoginService(manager);

		setSize(500,400);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		createLoginBlock();

		createLoginButton();
		
		setLayout(new BorderLayout());
		JLabel background = new JLabel(new ImageIcon("./src/main/java/loginBackground.jpg"));
		add(background);
		background.setLayout(new FlowLayout());

		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createLoginButton() {

		loginButton = new Button("Login");
		loginButton.setActionCommand("");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FormResponse<String> response = service.login(logText.getText(), 
						String.valueOf(passField.getPassword()));
				switch(response.getStatus()) {
					case ERROR : showErrorMessage(response.getMessage());
					break;
					case DONE : login();
					break;
				}
			}
		});
		loginButton.setBackground(Color.lightGray);
		loginButton.setForeground(Color.BLACK);
		loginButton.setFont(new Font("Courier", Font.BOLD, 16));
		loginButton.setBounds(200, 250, 100, 26);
		add(loginButton);
	}

	private void createLoginBlock() {

		logLabel = new JLabel("Login");
		logLabel.setBounds(150, 100, 120, 20);
		logLabel.setForeground(Color.WHITE);
		logLabel.setFont(new Font("Courier", Font.BOLD, 18));
		add(logLabel);

		logText = new JTextField();
		logText.setVisible(true);
		logText.setBounds(250, 100, 120, 20);
		add(logText);

		passLabel = new JLabel("Password");
		passLabel.setBounds(150, 150, 120, 20);
		passLabel.setForeground(Color.WHITE);
		passLabel.setFont(new Font("Courier", Font.BOLD, 18));
		add(passLabel);

		passField = new JPasswordField();
		passField.setVisible(true);
		passField.setBounds(250, 150, 120, 20);
		add(passField);

		labelMessage = new JLabel("Message");
		labelMessage.setBounds(150, 300, 340, 20);
		labelMessage.setVisible(true);
		labelMessage.setText("");
		labelMessage.setForeground(Color.red);
		labelMessage.setFont(new Font("Courier", Font.BOLD, 14));
		add(labelMessage);
	}
	
	private void changeLanguage(Language lang) {
		manager.getLocale().setLanguage(lang);
	}

	private void showErrorMessage(String message) {
		labelMessage.setText(message);
	}

	private void login() {
		manager.login(this);
	}
}
