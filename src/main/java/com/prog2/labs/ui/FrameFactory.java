package com.prog2.labs.ui;

import javax.swing.JFrame;

import com.prog2.labs.app.LibraryManager;
import com.prog2.labs.ui.library.LibraryFrame;
import com.prog2.labs.ui.login.LoginFrame;
import com.prog2.labs.ui.student.StudentFrame;

/**
 * The FrameFactory class.
 *  Factory creates UI Frames based on Sassion
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class FrameFactory {
	
	/**
	 * Method createFrame
	 *  Factory method creates JFrame instance based on Session
	 *  
	 * @param manager
	 * @return JFrame
	 */
	public JFrame createFrame(LibraryManager manager) {
		switch(manager.getSession()) {
			case STUDENT : return new StudentFrame(manager);
			case LIBRARY :return new LibraryFrame(manager);
		default:
			break;
		}
		return new LoginFrame(manager);
	}
}
