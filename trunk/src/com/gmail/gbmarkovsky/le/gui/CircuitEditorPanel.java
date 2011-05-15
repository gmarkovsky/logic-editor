package com.gmail.gbmarkovsky.le.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

import com.gmail.gbmarkovsky.le.elements.Connector.ConnectorType;
import com.gmail.gbmarkovsky.le.elements.GateType;
import com.gmail.gbmarkovsky.le.tools.ConnectorCreator;
import com.gmail.gbmarkovsky.le.tools.ElementDrugger;
import com.gmail.gbmarkovsky.le.tools.ElementSelector;
import com.gmail.gbmarkovsky.le.tools.GateCreator;
import com.gmail.gbmarkovsky.le.tools.PinSelector;
import com.gmail.gbmarkovsky.le.tools.SignalSetuper;
import com.gmail.gbmarkovsky.le.tools.WireChanger;
import com.gmail.gbmarkovsky.le.tools.WireCreator;
import com.gmail.gbmarkovsky.le.tools.WireSelector;

public class CircuitEditorPanel extends JPanel {
	private static final long serialVersionUID = -2541499089778347322L;
	
	private CircuitEditor circuitEditor;
	private JPanel controlPanel;
	
	private JToggleButton cursorButton;
	private JToggleButton wireButton;
	private JToggleButton andGateButton;
	private JToggleButton orGateButton;
	private JToggleButton notGateButton;
	private JToggleButton inputButton;
	private JToggleButton outputButton;
	
	CircuitEditorPanel() {
		initControls();
		initListeners();
	}
	
	private void initControls() {
		setLayout(new BorderLayout());
		circuitEditor =  new CircuitEditor();
		initControlPanel();
		
		circuitEditor.addCircuitTool(new ElementDrugger(circuitEditor));
		circuitEditor.addCircuitTool(new ElementSelector(circuitEditor));
		circuitEditor.addCircuitTool(new PinSelector(circuitEditor));
		circuitEditor.addCircuitTool(new WireChanger(circuitEditor));
		circuitEditor.addCircuitTool(new WireSelector(circuitEditor));
		circuitEditor.addCircuitTool(new SignalSetuper(circuitEditor));
		
		JScrollPane scrollPane = new JScrollPane(circuitEditor);
		add(scrollPane, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.NORTH);
	}
	
	private void initControlPanel(){
		controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setBorder(BorderFactory.createBevelBorder(1));
		
		ImageIcon imageIcon = new ImageIcon(CircuitEditorPanel.class.getResource("cursor.png"));
		ImageIcon wireIcon = new ImageIcon(CircuitEditorPanel.class.getResource("wire.png"));
		cursorButton = new JToggleButton(imageIcon, true);
		wireButton = new JToggleButton(wireIcon);
		andGateButton = new JToggleButton("AND");
		orGateButton = new JToggleButton("OR");
		notGateButton = new JToggleButton("NOT");
		inputButton = new JToggleButton("In");
		outputButton = new JToggleButton("Out");
		
		cursorButton.setToolTipText("Выделение, перемещение");
		wireButton.setToolTipText("Нарисовать провод");
		
		final int bw = 52;
		final int bh = 34;
		
		andGateButton.setPreferredSize(new Dimension(bw, bh));
		orGateButton.setPreferredSize(new Dimension(bw, bh));
		notGateButton.setPreferredSize(new Dimension(bw, bh));
		inputButton.setPreferredSize(new Dimension(bw, bh));
		outputButton.setPreferredSize(new Dimension(bw, bh));
		
        ButtonGroup group = new ButtonGroup();
        group.add(cursorButton);
        group.add(wireButton);
        group.add(andGateButton);
        group.add(orGateButton);
        group.add(notGateButton);
        group.add(inputButton);
        group.add(outputButton);
        JPanel radioPanel = new JPanel(new GridBagLayout());
        //radioPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        radioPanel.add(cursorButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(0, 0, 0, 0), 0, 0));
        JSeparator jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(wireButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(0, 0, 0, 0), 0, 0));
        jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(andGateButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(5, 0, 5, 0), 0, 0));
        radioPanel.add(orGateButton, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(5, 2, 5, 0), 0, 0));
        radioPanel.add(notGateButton, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(5, 2, 5, 0), 0, 0));
        jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(inputButton, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(5, 2, 5, 0), 0, 0));
        radioPanel.add(outputButton, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(5, 2, 5, 0), 0, 0));
        controlPanel.add(radioPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.NONE,
        		new Insets(0, 0, 0, 0), 0, 0));
	}
	
	private void initListeners() {
        cursorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new ElementDrugger(circuitEditor));
				circuitEditor.addCircuitTool(new ElementSelector(circuitEditor));
				circuitEditor.addCircuitTool(new PinSelector(circuitEditor));
				circuitEditor.addCircuitTool(new WireChanger(circuitEditor));
				circuitEditor.addCircuitTool(new WireSelector(circuitEditor));
				circuitEditor.addCircuitTool(new SignalSetuper(circuitEditor));
			}
		});
        wireButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new WireCreator(circuitEditor));
				circuitEditor.addCircuitTool(new PinSelector(circuitEditor));
			}
		});
        andGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new GateCreator(circuitEditor, GateType.AND));
			}
		});
        orGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new GateCreator(circuitEditor, GateType.OR));
			}
		});
        notGateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new GateCreator(circuitEditor, GateType.NOT));
			}
		});
        inputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new ConnectorCreator(circuitEditor, ConnectorType.INPUT));
			}
		});
        outputButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new ConnectorCreator(circuitEditor, ConnectorType.OUTPUT));
			}
		});
	}

	public CircuitEditor getCircuitEditor() {
		return circuitEditor;
	}
}
