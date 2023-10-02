package com.prog2.labs.app;

import javax.swing.SwingUtilities;

/**
 * The Library Application class.
 * 	Create Service class object and run start Application
 * 	Then create and run UI
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class LibraryApplication {

	/**
	 * Write your test code below in the main (optional).
	 *
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	new LibraryManager().initFrame();
            }
        });
	}

}


