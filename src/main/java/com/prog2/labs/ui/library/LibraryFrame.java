package com.prog2.labs.ui.library;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.i18n.LibraryLocale;
import com.prog2.labs.service.LibraryService;
import com.prog2.labs.ui.AbstractLocalizedFrame;

import static com.prog2.labs.app.commons.Constants.TAB;

/**
 * The LibraryFrame class.
 * 		extends AbstractLocalizedFrame
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class LibraryFrame extends AbstractLocalizedFrame {

	private static final String BUTTON_LOGOUT = null;
	private LibraryService service;
	private LibraryManager manager;
	private LibraryLocale locale;
	
	private LibraryService catalogService;	
	private CatalogPanel catalogPanel;
	private IssuePanel issuePanel;
	private ManageLibraryPanel manageLibraryPanel;
	private ReturnPanel returnPanel;
	
	private JLabel userIdLabel;
	private Button logOutButton;

	JTabbedPane tabPanel;
	JRadioButton engRadio;
	JRadioButton frRadio;
	JLabel engLabel;
	JLabel frLabel;
	ButtonGroup radioGroup;
	

	public LibraryFrame(LibraryManager manager) {
		super(manager.getLocale());
		service = new LibraryService();
		catalogService = new LibraryService();
		
		this.manager = manager;
		locale = manager.getLocale();
		
		catalogPanel = new CatalogPanel(catalogService,this.manager);
		issuePanel = new IssuePanel(service, this.manager);
		manageLibraryPanel = new ManageLibraryPanel(service, this.manager);
		returnPanel = new ReturnPanel(service, this.manager);
		
		init();
	}
	
	private void init() {

		setSize(1000,740);
		setLayout(null);
		
		// TOP
		createTop();
		
		// Tabs
		createTubPanel();
		
		// Footer
		createLogOut();
		
		initLocalaizedText();

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void createTubPanel() {
		tabPanel = new JTabbedPane();
		tabPanel.setBounds(16,50,950,572);  
		getContentPane().add(tabPanel);
		tabPanel.add("", issuePanel);
		tabPanel.add("", manageLibraryPanel);
		tabPanel.add("", returnPanel);
		tabPanel.add("", catalogPanel);
		add(tabPanel);
	}

	public void initLocalaizedText() {
		catalogPanel.initLocalaizedText();
		issuePanel.initLocalaizedText();
		manageLibraryPanel.initLocalaizedText();
		returnPanel.initLocalaizedText();
		
		tabPanel.setTitleAt(0, TAB + locale.get("issueBooks") + TAB);
		tabPanel.setTitleAt(2, TAB + locale.get("ReturnBooks") + TAB);
		tabPanel.setTitleAt(3, TAB + locale.get("catalog") + TAB);
		tabPanel.setTitleAt(1, TAB + locale.get("ManageLibrary") + TAB);
		
		logOutButton.setLabel(locale.get("logout"));
		userIdLabel.setText(manager.getUserInfo());
	}

	private void createLogOut() {
		logOutButton = new Button("");
		logOutButton.setActionCommand("LogOut");
		logOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		logOutButton.setBackground(Color.LIGHT_GRAY);
		logOutButton.setForeground(Color.BLACK);
		logOutButton.setFont(new Font("Dialog", Font.BOLD, 11));
		logOutButton.setBounds(750, 640, 157, 25);
		add(logOutButton);
	}

	private void createTop() {
		// Create Label ID
		userIdLabel = new JLabel();
		userIdLabel.setBounds(20, 20, 500, 14);
		add(userIdLabel);
	}

	private void logout() {
		manager.logout(this);
	}
}
