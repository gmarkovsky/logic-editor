package com.gmail.gbmarkovsky.gui;

import javax.swing.JFrame;

/**
 * Окно редактора логических схем.
 * @author george
 *
 */
public class LogicEditorFrame extends JFrame {
	private static final long serialVersionUID = 8808280554536347790L;
	private static final int WIDTH = 750;
	private static final int HEIGHT = 500;

	public LogicEditorFrame() {
		setTitle("Редактор логических схем");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new CircuitEditor());
	}
}
