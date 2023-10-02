package com.prog2.labs.app;

import javax.swing.JFrame;

import com.prog2.labs.db.entity.User;
import com.prog2.labs.i18n.Language;
import com.prog2.labs.i18n.LibraryLocale;
import com.prog2.labs.model.SessionType;
import com.prog2.labs.ui.FrameFactory;

/**
 * The Library Manager class.
 * 	Manages all session operations
 *  Sets Session based on user role and run UI
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class LibraryManager {

	private SessionType session;
	private LibraryLocale locale;
	private User user;
	private FrameFactory factory;

	public LibraryManager() {
		session = SessionType.LOGIN;
		// Set first language ENGLISH
		locale = LibraryLocale.createLocale(Language.ENGLISH);
		factory = new FrameFactory();
	}

	public void initFrame() {
		factory.createFrame(this);
	}

	public void login(JFrame frame) {
		initFrame();
		destroyActiveFrame(frame);
	}

	public void logout(JFrame frame) {
		setSession(SessionType.LOGIN);
		initFrame();
		destroyActiveFrame(frame);
	}

	private void destroyActiveFrame(JFrame frame) {
		frame.setVisible(false); //you can't see me!
		frame.dispose(); //Destroy the JFrame object
	}

	public SessionType getSession() {
		return session;
	}

	public void setSession(SessionType session) {
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LibraryLocale getLocale() {
		return locale;
	}

	public String getUserInfo() {
		return locale.get("role") + ": " + String.valueOf(getUser().getRole()) 
				+ " | " 
				+ locale.get("user") + ": " + getUser().getName() 
				+ " | "
				+ locale.get("userId") + ": " + getUser().getUser_id();
	}

	@Override
	public String toString() {
		return "LibraryManager [session=" + session + ", locale=" + locale + ", user=" + user + ", factory=" + factory
				+ "]";
	}
}
