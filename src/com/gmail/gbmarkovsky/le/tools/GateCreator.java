package com.gmail.gbmarkovsky.le.tools;

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
public class GateCreator implements CircuitTool {
	private CircuitEditor editor;
	private GateType gateType;
	
	public GateCreator(CircuitEditor editor, GateType gateType) {
		this.editor = editor;
		this.gateType = gateType;
	}
	
	public GateType getGateType() {
		return gateType;
	}

	public void setGateType(GateType gateType) {
		this.gateType = gateType;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = editor.getCircuit();
		CircuitView circuitView = editor.getCircuitView();
		Gate gate = new LogicCell(gateType);
		GateView gateView = new LogicCellView(new Point(arg0.getX(), arg0.getY()), gate);
		circuit.addGate(gate);
		circuitView.addGateView(gateView);
		editor.updateSize();
		editor.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
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
		
	}

}
