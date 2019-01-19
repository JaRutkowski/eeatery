package com.javafee.eeatery;

import javax.swing.SwingUtilities;

import com.javafee.startform.Actions;

public class Main {

	public static void main(String[] args) {
		// ConsoleApplication.run();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Actions actions = new Actions();
					actions.control();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
