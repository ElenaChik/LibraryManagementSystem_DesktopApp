package com.prog2.labs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.prog2.labs.i18n.Language;
import com.prog2.labs.i18n.LibraryLocale;
import com.prog2.labs.i18n.Localizable;

import static com.prog2.labs.app.commons.Constants.ENGLISH_LANG;
import static com.prog2.labs.app.commons.Constants.FRENCH_LANG;

/**
 * The AbstractLocalizedFrame abstract class.
 * 		extends JFrame implements Localizable
 * 	implements localization UI and switch language methods
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public abstract class AbstractLocalizedFrame extends JFrame implements Localizable {

	private static final long serialVersionUID = 1L;

	protected JRadioButton engRadio;
	protected JRadioButton frRadio;
	protected JLabel engLabel;
	protected JLabel frLabel;
	protected ButtonGroup radioGroup;
	protected LibraryLocale locale;

	protected AbstractLocalizedFrame(LibraryLocale locale) {
		this.locale = locale;

		createLanguageZone();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public abstract void initLocalaizedText();
	
	private void changeLanguage(Language lang) {
		locale.setLanguage(lang);
		this.initLocalaizedText();
	}

	private void createLanguageZone() {
		// Create Language block
		engLabel = new JLabel(ENGLISH_LANG);
		engLabel.setBounds(880, 20, 50, 15);
		add(engLabel);
		
		engRadio = new JRadioButton(ENGLISH_LANG, true);
		engRadio.setBounds(850, 20, 20, 15);
		engRadio.setText(ENGLISH_LANG);
		add(engRadio);
		
		frLabel = new JLabel(FRENCH_LANG);
		frLabel.setBounds(880, 40, 50, 15);
		add(frLabel);
		
		frRadio = new JRadioButton(FRENCH_LANG);
		frRadio.setBounds(850, 40, 20, 15);
		frRadio.setText(FRENCH_LANG);
		add(frRadio);
		
		radioGroup = new ButtonGroup();
		radioGroup.add(engRadio);
		radioGroup.add(frRadio);
		
		// Add listener
		engRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Language.ENGLISH);
			}
		});

		//add disallow listener
		frRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Language.FRENCH);
			}
		});
	}
}
