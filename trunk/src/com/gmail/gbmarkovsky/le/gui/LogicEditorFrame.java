package com.gmail.gbmarkovsky.le.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import com.gmail.gbmarkovsky.le.engine.GateCreator;
import com.gmail.gbmarkovsky.le.engine.GateDrugger;
import com.gmail.gbmarkovsky.le.engine.GateType;
import com.gmail.gbmarkovsky.le.engine.InputCreator;
import com.gmail.gbmarkovsky.le.engine.OutputCreator;

/**
 * Окно редактора логических схем.
 * @author george
 *
 */
public class LogicEditorFrame extends JFrame {
	private static final long serialVersionUID = 8808280554536347790L;
	private static final int WIDTH = 750;
	private static final int HEIGHT = 500;
	
	private CircuitEditor circuitEditor;
	private JPanel controlPanel;
	
	private JToggleButton cursorButton;
	private JToggleButton andGateButton;
	private JToggleButton orGateButton;
	private JToggleButton notGateButton;
	private JToggleButton inputButton;
	private JToggleButton outputButton;
	
	public LogicEditorFrame() {
		setTitle("Редактор логических схем");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initControls();
		initListeners();
	}
	
	private void initControls() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		circuitEditor =  new CircuitEditor();
		initMainMenu();
		initControlPanel();
		contentPane.add(circuitEditor, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.WEST);
	}
	
	private void initMainMenu() {
		JMenuBar menuBar  = new JMenuBar();
		JMenu mFile = new JMenu("Файл");
		JMenuItem miExit = new JMenuItem("Выход");
		miExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		miExit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.ALT_MASK));
		mFile.add(miExit);
		menuBar.add(mFile);
		setJMenuBar(menuBar);
	}
	
	private void initControlPanel() {
		controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setBorder(BorderFactory.createBevelBorder(1));
		cursorButton = new JToggleButton("A", true);
		andGateButton = new JToggleButton("AND");
		orGateButton = new JToggleButton("OR");
		notGateButton = new JToggleButton("NOT");
		inputButton = new JToggleButton("In");
		outputButton = new JToggleButton("Out");
        ButtonGroup group = new ButtonGroup();
        group.add(cursorButton);
        group.add(andGateButton);
        group.add(orGateButton);
        group.add(notGateButton);
        group.add(inputButton);
        group.add(outputButton);
        JPanel radioPanel = new JPanel(new GridBagLayout());
        radioPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        radioPanel.add(cursorButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        radioPanel.add(andGateButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        radioPanel.add(orGateButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        radioPanel.add(notGateButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        radioPanel.add(inputButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        radioPanel.add(outputButton, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        controlPanel.add(radioPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(0, 0, 0, 0), 0, 0));
	}
	
	private void initListeners() {
        cursorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new GateDrugger(circuitEditor));
			}
		});
        andGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new GateCreator(circuitEditor, GateType.AND));
			}
		});
        orGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new GateCreator(circuitEditor, GateType.OR));
			}
		});
        notGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new GateCreator(circuitEditor, GateType.NOT));
			}
		});
        inputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new InputCreator(circuitEditor));
			}
		});
        outputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.setCurrentTool(new OutputCreator(circuitEditor));
			}
		});
	}
}
