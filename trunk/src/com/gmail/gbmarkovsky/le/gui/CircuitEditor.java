package com.gmail.gbmarkovsky.le.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.tools.CircuitTool;
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
	//private GateCreator gateCreator;
	private CircuitTool tool;
	private WireCreator wireCreator;
	private WireSelectionTool wireSelectionTool;
	
	public CircuitEditor() {
		circuit = new Circuit();
		circuitView = new CircuitView(circuit);
		wireCreator = new WireCreator(this);
		wireSelectionTool = new WireSelectionTool(this);
		addMouseListener(wireCreator);
		addMouseMotionListener(wireCreator);
		PinSelectionTool pinSelectionTool = new PinSelectionTool(this);
		addMouseListener(pinSelectionTool);
		addMouseMotionListener(pinSelectionTool);
		setBackground(Color.white);
		setForeground(Color.white);
//		circuitView.addElementView(new SevenSegmentsIndicatorView(new Point(40, 50),
//				new SevenSegmentsIndicator()));
	}
	
	public void paint(Graphics g) {
		circuitView.paint(g);
		for (MouseListener ml: getMouseListeners()) {
			if (ml instanceof WireCreator) {
				((WireCreator) ml).paint(g);
			}
			if (ml instanceof PinSelectionTool) {
				((PinSelectionTool) ml).paint(g);
			}
			if (ml instanceof WireSelectionTool) {
				((WireSelectionTool) ml).paint(g);
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
	}
	
	public void removeWireC() {
		removeMouseListener(wireCreator);
		removeMouseMotionListener(wireCreator);
		removeMouseListener(wireSelectionTool);
		removeMouseMotionListener(wireSelectionTool);
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
}
