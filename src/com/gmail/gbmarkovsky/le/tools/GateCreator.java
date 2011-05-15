package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.GateType;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.GateView;

/**
 * Предназначен для создания логических элементов в ответ на действия пользователя.
 * @author george
 *
 */
public class GateCreator extends AbstractCircuitTool {
	private GateType gateType;
	private GateView gateView;
	private boolean drawFantom;
	private boolean canPlace = true;
	
	public GateCreator(CircuitEditor circuitEditor, GateType gateType) {
		super(circuitEditor);
		this.gateType = gateType;
		Gate gate = new Gate(this.gateType);
		gateView = new GateView(new Point(0, 0), gate);
		gateView.setFantom();
	}
	
	public GateType getGateType() {
		return gateType;
	}

	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (canPlace) {
			Circuit circuit = circuitEditor.getCircuit();
			CircuitView circuitView = circuitEditor.getCircuitView();
			Gate gate = new Gate(gateType);
			Point toPoint = new Point(arg0.getX() - gateView.getWidth(), arg0.getY() - gateView.getHeight());
			GateView gateView = new GateView(toPoint, gate);
			circuit.addElement(gate);
			circuitView.addElementView(gateView);
			circuitEditor.updateSize();
			circuitEditor.repaint();
			canPlace = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawFantom = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		drawFantom = false;
		circuitEditor.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point toPoint = new Point(arg0.getX() - gateView.getWidth(), arg0.getY() - gateView.getHeight());
		gateView.setPosition(toPoint);
		circuitEditor.repaint();
		if (arg0.getX() - gateView.getWidth() < 0 || arg0.getY() - gateView.getHeight() < 0) {
			canPlace = false;
			drawFantom = false;
		} else {
			canPlace = true;
			drawFantom = true;
		}
	}
	
	public void paint(Graphics g) {
		if (drawFantom) {
			gateView.paint(g);
		}
	}

}
