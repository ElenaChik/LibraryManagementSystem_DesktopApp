package com.prog2.labs.ui.student;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.i18n.LibraryLocale;
import com.prog2.labs.service.LibraryService;
import com.prog2.labs.service.StudentService;
import com.prog2.labs.ui.AbstractLocalizedFrame;
import com.prog2.labs.ui.library.CatalogPanel;

import static com.prog2.labs.app.commons.Constants.TAB;

/**
 * The StudentFrame class.
 * 		extends AbstractLocalizedFrame
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class StudentFrame extends AbstractLocalizedFrame {

	private static final long serialVersionUID = 1L;

	private LibraryService catalogService;
	private StudentService service;
	private LibraryManager manager;
	private LibraryLocale locale;

	private BorrowPanel borrowPanel;
	private CatalogPanel catalogPanel;

	private JTabbedPane tabPanel;
	private JLabel userIdLabel;

	private Button logOutButton;

	public StudentFrame(LibraryManager manager) {
		super(manager.getLocale());
		service = new StudentService();
		catalogService = new LibraryService();

		this.manager = manager;
		locale = manager.getLocale();

		borrowPanel = new BorrowPanel(service,this.manager);
		catalogPanel = new CatalogPanel(catalogService,this.manager);

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

	private void createTubPanel() {
		tabPanel = new JTabbedPane();
		tabPanel.setBounds(16,50,950,572);  
		getContentPane().add(tabPanel);
		tabPanel.add("", borrowPanel);
		tabPanel.add("", catalogPanel);		
		add(tabPanel);
	}

	@Override
	public void initLocalaizedText() {
		borrowPanel.initLocalaizedText();
		catalogPanel.initLocalaizedText();

		tabPanel.setTitleAt(0, TAB + locale.get("manageBooks") + TAB);
		tabPanel.setTitleAt(1, TAB + locale.get("catalog") + TAB);

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
