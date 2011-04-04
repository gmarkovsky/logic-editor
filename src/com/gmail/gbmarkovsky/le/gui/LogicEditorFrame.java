package com.gmail.gbmarkovsky.le.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.gmail.gbmarkovsky.le.io.CircuitSerializer;
import com.gmail.gbmarkovsky.le.views.CircuitView;

/**
 * Окно редактора логических схем.
 * @author george
 *
 */
public class LogicEditorFrame extends JFrame {
	private static final long serialVersionUID = 8808280554536347790L;
	private static final int WIDTH = 750;
	private static final int HEIGHT = 500;
	
	private CircuitEditorPanel editorPanel;
	
	public LogicEditorFrame() {
		setTitle("Редактор логических схем");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorPanel = new CircuitEditorPanel();
		add(editorPanel);
		initMainMenu();
	}

	
	private void initMainMenu() {
		JMenuBar menuBar  = new JMenuBar();
		JMenu mFile = new JMenu("Файл");
		JMenu mEdit = new JMenu("Правка");
		JMenuItem miExit = new JMenuItem("Выход");
		miExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		miExit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.ALT_MASK));
		
		JMenuItem miOpen = new JMenuItem("Открыть");
		miOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileInputStream fw = null;
				try {
					fw = new FileInputStream("test.xml");
				} catch (IOException e) {
					e.printStackTrace();
				}
				CircuitView circuitView = null;
				byte[] bytes = null;
				try {
					bytes = new byte[fw.available()];
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					fw.read(bytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
				circuitView = CircuitSerializer.parse(arrayInputStream);
				editorPanel.getCircuitEditor().setCircuit(circuitView.getCircuit());
				editorPanel.getCircuitEditor().setCircuitView(circuitView);
				editorPanel.getCircuitEditor().repaint();
			}
		});
		
		mFile.add(miOpen);
		
		JMenuItem miSave = new JMenuItem("Сохранить");
		miSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileOutputStream fw = null;
				try {
					fw = new FileOutputStream("test.xml");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					CircuitSerializer.write(editorPanel.getCircuitEditor().getCircuitView()).writeTo(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		mFile.add(miSave);
		mFile.add(miExit);
		menuBar.add(mFile);
		
		JMenuItem miDelete = new JMenuItem("Удалить");
		miDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editorPanel.getCircuitEditor().deleteSelectedElements();
			}
		});
		miDelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		mEdit.add(miDelete);
		menuBar.add(mEdit);
		
		setJMenuBar(menuBar);
	}
	

}
