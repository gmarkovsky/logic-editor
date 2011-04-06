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
import com.gmail.gbmarkovsky.le.tools.CircuitTool;
import com.gmail.gbmarkovsky.le.tools.ElementSelector;
import com.gmail.gbmarkovsky.le.tools.PinSelectionTool;
import com.gmail.gbmarkovsky.le.tools.WireCreator;
import com.gmail.gbmarkovsky.le.tools.WireSelectionTool;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.ElementView;

/**
 * Компонент для отображения и редактирования логической схемы.
 * @author george
 *
 */
public class CircuitEditor extends JComponent {
	private static final long serialVersionUID = -2235220973441036453L;
	private Circuit circuit;
	private CircuitView circuitView;
	private CircuitTool tool;
	private WireCreator wireCreator;
	private WireSelectionTool wireSelectionTool;
	private List<ElementView> selectedElements = new ArrayList<ElementView>();
	private ElementSelector elementSelector;
	
	public CircuitEditor() {
		circuit = new Circuit();
		circuitView = new CircuitView(circuit);
		circuit.addPropertyChangeListener(circuitView);
		wireCreator = new WireCreator(this);
		elementSelector = new ElementSelector(this);
		setWireC();
		PinSelectionTool pinSelectionTool = new PinSelectionTool(this);
		addMouseListener(pinSelectionTool);
		addMouseMotionListener(pinSelectionTool);
		setBackground(Color.white);
		setForeground(Color.white);
//		circuitView.addElementView(new SevenSegmentsIndicatorView(new Point(40, 50),
//				new SevenSegmentsIndicator()));
	}

	public void setSelectedElement(ElementView selectedElement) {
		selectedElements.clear();
		selectedElements.add(selectedElement);
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public void setCircuitView(CircuitView circuitView) {
		this.circuitView = circuitView;
	}

	public void paint(Graphics g) {
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
	
	public void setCurrentTool(CircuitTool tool) {
		removeMouseListener(this.tool);
		removeMouseMotionListener(this.tool);
		this.tool = tool;
		addMouseListener(this.tool);
		addMouseMotionListener(this.tool);
	}
	
	public void setWireC() {
		addMouseListener(wireCreator);
		addMouseMotionListener(wireCreator);
		addMouseListener(wireSelectionTool);
		addMouseMotionListener(wireSelectionTool);
		addMouseListener(elementSelector);
		addMouseMotionListener(elementSelector);
	}
	
	public void removeWireC() {
		removeMouseListener(wireCreator);
		removeMouseMotionListener(wireCreator);
		removeMouseListener(wireSelectionTool);
		removeMouseMotionListener(wireSelectionTool);
		removeMouseListener(elementSelector);
		removeMouseMotionListener(elementSelector);
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
		for (ElementView ew: selectedElements) {
			circuitView.deleteElementView(ew);
			repaint();
		}
		selectedElements.clear();
	}
	
	public List<ElementView> getSelectedElements() {
		return selectedElements;
	}
}
