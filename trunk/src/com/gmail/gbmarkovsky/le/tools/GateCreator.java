package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.GateType;
import com.gmail.gbmarkovsky.le.elements.LogicCell;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.GateView;
import com.gmail.gbmarkovsky.le.views.LogicCellView;

/**
 * Предназначен для создания логических элементов в ответ на действия пользователя.
 * @author george
 *
 */
public class GateCreator extends AbstractCircuitTool {
	private GateType gateType;
	private GateView gateView;
	private boolean drawFantom;
	
	public GateCreator(CircuitEditor circuitEditor, GateType gateType) {
		super(circuitEditor);
		this.gateType = gateType;
		Gate gate = new LogicCell(this.gateType);
		gateView = new LogicCellView(new Point(0, 0), gate);
	}
	
	public GateType getGateType() {
		return gateType;
	}

	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = circuitEditor.getCircuit();
		CircuitView circuitView = circuitEditor.getCircuitView();
		Gate gate = new LogicCell(gateType);
		GateView gateView = new LogicCellView(new Point(arg0.getX(), arg0.getY()), gate);
		circuit.addGate(gate);
		circuitView.addGateView(gateView);
		circuitEditor.updateSize();
		circuitEditor.repaint();
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
		gateView.setPosition(arg0.getPoint());
		circuitEditor.repaint();
	}
	
	public void paint(Graphics g) {
		if (drawFantom) {
			gateView.paint(g);
		}
	}

}
