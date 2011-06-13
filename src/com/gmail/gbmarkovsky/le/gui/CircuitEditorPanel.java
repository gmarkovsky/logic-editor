/*
 * Copyright (c) 2011, Georgy Markovsky 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 *  * Neither the name of  nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package com.gmail.gbmarkovsky.le.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import com.gmail.gbmarkovsky.le.tools.IndicatorCreator;
import com.gmail.gbmarkovsky.le.tools.PinSelector;
import com.gmail.gbmarkovsky.le.tools.SignalSetuper;
import com.gmail.gbmarkovsky.le.tools.WireChanger;
import com.gmail.gbmarkovsky.le.tools.WireCreator;
import com.gmail.gbmarkovsky.le.tools.WireCutter;

public class CircuitEditorPanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = -2541499089778347322L;
	
	private CircuitEditor circuitEditor;
	private JPanel controlPanel;
	
	private JToggleButton cursorButton;
	private JButton deleteButton;
	private JToggleButton wireButton;
	private JToggleButton andGateButton;
	private JToggleButton orGateButton;
	private JToggleButton notGateButton;
	private JToggleButton inputButton;
	private JToggleButton yesButton;
	private JToggleButton outputButton;
	private JToggleButton indicatorButton;
	
	public CircuitEditorPanel() {
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
		circuitEditor.addCircuitTool(new SignalSetuper(circuitEditor));
		
		circuitEditor.addPropertyChangeListener(this);
		
		JScrollPane scrollPane = new JScrollPane(circuitEditor);
		add(scrollPane, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.NORTH);
	}
	
	private void initControlPanel(){
		controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setBorder(BorderFactory.createBevelBorder(1));
		
		ImageIcon imageIcon = new ImageIcon(CircuitEditorPanel.class.getResource("select.png"));
		ImageIcon wireIcon = new ImageIcon(CircuitEditorPanel.class.getResource("wire.png"));
		ImageIcon deleteIcon = new ImageIcon(CircuitEditorPanel.class.getResource("cancel.png"));
		cursorButton = new JToggleButton(imageIcon, true);
		wireButton = new JToggleButton(wireIcon);
		deleteButton = new JButton(deleteIcon);
		andGateButton = new JToggleButton(" И ");
		orGateButton = new JToggleButton("ИЛИ");
		notGateButton = new JToggleButton("НЕ");
		inputButton = new JToggleButton("Вход");
		yesButton = new JToggleButton("ДА");
		outputButton = new JToggleButton("Выход");
		indicatorButton = new JToggleButton("Индикатор");
		
		andGateButton.setToolTipText("И");
		orGateButton.setToolTipText("ИЛИ");
		notGateButton.setToolTipText("Отрицание");
		inputButton.setToolTipText("Вход схемы");
		yesButton.setToolTipText("Тождественная функция");
		outputButton.setToolTipText("Выход схемы");
		indicatorButton.setToolTipText("Семисегментный индикатор");
		
		deleteButton.setToolTipText("Удалить выделенные элементы");
		cursorButton.setToolTipText("Выделение, перемещение");
		wireButton.setToolTipText("Нарисовать провод");
		
		deleteButton.setEnabled(false);
		
//		final int bw = 52;
//		final int bh = 34;
//		
//		andGateButton.setPreferredSize(new Dimension(bw, bh));		
//		orGateButton.setPreferredSize(new Dimension(bw, bh));
//		notGateButton.setPreferredSize(new Dimension(bw, bh));
//		inputButton.setPreferredSize(new Dimension(bw, bh));
//		outputButton.setPreferredSize(new Dimension(bw, bh));
		
        ButtonGroup group = new ButtonGroup();
        group.add(cursorButton);
        group.add(wireButton);
        group.add(andGateButton);
        group.add(orGateButton);
        group.add(notGateButton);
        group.add(inputButton);
        group.add(yesButton); 
        group.add(outputButton);
        group.add(indicatorButton);
        
        JPanel radioPanel = new JPanel(new GridBagLayout());
        //radioPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        radioPanel.add(cursorButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        
        radioPanel.add(deleteButton, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        
        JSeparator jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
		
        radioPanel.add(wireButton, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(0, 0, 0, 0), 0, 0));
        
        jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(andGateButton, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 0, 5, 0), 0, 0));
        radioPanel.add(orGateButton, new GridBagConstraints(6, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 2, 5, 0), 0, 0));
        radioPanel.add(notGateButton, new GridBagConstraints(7, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 2, 5, 0), 0, 0));
        radioPanel.add(yesButton, new GridBagConstraints(8, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 2, 5, 0), 0, 0));
        
        jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(inputButton, new GridBagConstraints(10, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 2, 5, 0), 0, 0));

        radioPanel.add(outputButton, new GridBagConstraints(11, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        		new Insets(5, 2, 5, 0), 0, 0));
        
        jSeparator = new JSeparator(JSeparator.VERTICAL);
        jSeparator.setPreferredSize(new Dimension(1, 1));
		radioPanel.add(jSeparator, new GridBagConstraints(12, 0, 1, 1, 0.0, 0.0,
        		GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
        		new Insets(5, 3, 5, 3), 0, 0));
        radioPanel.add(indicatorButton, new GridBagConstraints(13, 0, 1, 1, 1.0, 1.0,
        		GridBagConstraints.CENTER, GridBagConstraints.BOTH,
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
				//circuitEditor.addCircuitTool(new WireSelector(circuitEditor));
				circuitEditor.addCircuitTool(new SignalSetuper(circuitEditor));
			}
		});
        deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.deleteSelectedElements();
				circuitEditor.deleteSelectedWire();
				circuitEditor.repaint();
			}
		});
        wireButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new WireCreator(circuitEditor));
				circuitEditor.addCircuitTool(new PinSelector(circuitEditor));
				circuitEditor.addCircuitTool(new WireCutter(circuitEditor));
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
        yesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new GateCreator(circuitEditor, GateType.CONST));
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
        indicatorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				circuitEditor.clearCircuitTools();
				circuitEditor.addCircuitTool(new IndicatorCreator(circuitEditor));
			}
		});
	}

	public CircuitEditor getCircuitEditor() {
		return circuitEditor;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(CircuitEditor.SELECTION_APPEARED)) {
			deleteButton.setEnabled(true);
		} else if (evt.getPropertyName().equals(CircuitEditor.SELECTION_CLEARED)) {
			deleteButton.setEnabled(false);
		}
	}
}
