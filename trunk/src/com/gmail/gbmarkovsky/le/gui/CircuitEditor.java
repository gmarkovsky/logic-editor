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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.tools.CircuitTool;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.ElementView;
import com.gmail.gbmarkovsky.le.views.WireView;

/**
 * Компонент для отображения и редактирования логической схемы.
 * @author george
 *
 */
public class CircuitEditor extends JComponent {
	private static final long serialVersionUID = -2235220973441036453L;
	
	private Circuit circuit;
	private CircuitView circuitView;
	
	private WireView selectedWireView;
	private Signal draggedSignal;
	private List<ElementView> selectedElements = new ArrayList<ElementView>();
	
	private ArrayList<CircuitTool> tools = new ArrayList<CircuitTool>();
	
	public CircuitEditor() {
		circuit = new Circuit();
		circuitView = new CircuitView(circuit);
		circuit.addPropertyChangeListener(circuitView);

		setDoubleBuffered(true);
		setBackground(Color.white);
		setForeground(Color.white);
	}

	public void setSelectedElement(ElementView selectedElement) {
		for(ElementView ev : selectedElements) {
			ev.setSelected(false);
		}
		selectedElements.clear();
		if (selectedElement != null) {
			selectedElement.setSelected(true);
			this.firePropertyChange(SELECTION_APPEARED, false, true);
		} else {
			this.firePropertyChange(SELECTION_CLEARED, true, false);
		}
		selectedElements.add(selectedElement);
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public void setCircuitView(CircuitView circuitView) {
		this.circuitView = circuitView;
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getSize().width, getSize().height);
		circuitView.paint(g);
		for (MouseListener ml: getMouseListeners()) {
			if (ml instanceof CircuitTool) {
				((CircuitTool) ml).paint(g);
			}
		}
	}

	public Circuit getCircuit() {
		return circuit;
	}

	public CircuitView getCircuitView() {
		return circuitView;
	}
	
	public void addCircuitTool(CircuitTool tool) {
		tools.add(tool);
		addMouseListener(tool);
		addMouseMotionListener(tool);
	}
	
	public void clearCircuitTools() {
		for(CircuitTool tool: tools) {
			removeMouseListener(tool);
			removeMouseMotionListener(tool);
		}
		tools.clear();
	}
	
	public void updateSize() {
		Dimension dimension = new Dimension();
		for (ElementView element: circuitView.getElements()) {
			Point point = element.getPosition();
			if ((point.x + element.getWidth()) > dimension.width) {
				dimension.width = point.x + element.getWidth() + 20;
			}
			if ((point.y + element.getHeight()) > dimension.height) {
				dimension.height = point.y + element.getHeight() + 20;
			}
		}
		setSize(dimension);
		setPreferredSize(dimension);
	}
	
	public void deleteSelectedElements() {
		if (!selectedElements.isEmpty()) {
			for (ElementView ew: selectedElements) {
				if (!ew.isReadOnly()) {
					circuitView.deleteElementView(ew);
					repaint();
				}
			}
			selectedElements.clear();
			this.firePropertyChange(SELECTION_CLEARED, true, false);
		}
	}
	
	public List<ElementView> getSelectedElements() {
		return selectedElements;
	}

	public WireView getSelectedWireView() {
		return selectedWireView;
	}
	
	public void setSelectedWireView(WireView selectedWireView) {
		if (selectedWireView != null) {
			this.selectedWireView = selectedWireView;
			this.selectedWireView.setSelected(true);
			this.firePropertyChange(SELECTION_APPEARED, false, true);
		}
	}

	public Signal getDraggedSignal() {
		return draggedSignal;
	}

	public void setDraggedSignal(Signal draggedSignal) {
		this.draggedSignal = draggedSignal;
	}

	public void appearSelection(List<ElementView> list) {
		selectedElements = list;
		for(ElementView ev : selectedElements) {
			ev.setSelected(true);
		}
		if (!selectedElements.isEmpty()) {
			this.firePropertyChange(SELECTION_APPEARED, false, true);
		}
	}
	
	public void clearSelection() {
		for(ElementView ev : selectedElements) {
			ev.setSelected(false);
		}
		selectedElements.clear();
		
		if (selectedWireView != null){
			selectedWireView.setSelected(false);
		}
		selectedWireView = null;
		
		this.firePropertyChange(SELECTION_CLEARED, true, false);
	}
	
	public void deleteSelectedWire() {
		if (selectedWireView != null) {
			circuitView.deleteWire(selectedWireView);
			selectedWireView = null;
			this.firePropertyChange(SELECTION_CLEARED, true, false);
			repaint();
		}
	}
	
	public static final String SELECTION_CLEARED = "selectionCleared";
	public static final String SELECTION_APPEARED = "selectionAppeared";
}
