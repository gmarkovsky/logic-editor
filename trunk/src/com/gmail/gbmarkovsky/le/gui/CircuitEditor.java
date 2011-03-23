package com.gmail.gbmarkovsky.le.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import com.gmail.gbmarkovsky.le.engine.Circuit;
import com.gmail.gbmarkovsky.le.engine.CircuitTool;
import com.gmail.gbmarkovsky.le.engine.WireCreator;
import com.gmail.gbmarkovsky.le.views.CircuitView;

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
	
	public CircuitEditor() {
		circuit = new Circuit();
		circuitView = new CircuitView(circuit);
		WireCreator wireCreator = new WireCreator(this);
		addMouseListener(wireCreator);
		addMouseMotionListener(wireCreator);
		setBackground(Color.white);
		setForeground(Color.white);
	}
	
	public void paint(Graphics g) {
		circuitView.paint(g);
		for (MouseListener ml: getMouseListeners()) {
			if (ml instanceof WireCreator) {
				((WireCreator) ml).paint(g);
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
}
