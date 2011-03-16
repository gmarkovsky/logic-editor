package com.gmail.gbmarkovsky.engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.gmail.gbmarkovsky.gui.CircuitEditor;
import com.gmail.gbmarkovsky.views.CircuitView;
import com.gmail.gbmarkovsky.views.GateView;
import com.gmail.gbmarkovsky.views.LogicCellView;

/**
 * Предназначен для создания логических элементов в ответ на действия пользователя.
 * @author george
 *
 */
public class GateCreator implements MouseListener {
	private CircuitEditor editor;
	
	public GateCreator(CircuitEditor editor) {
		this.editor = editor;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = editor.getCircuit();
		CircuitView circuitView = editor.getCircuitView();
		Gate gate = LogicCell.createAndGate();
		GateView gateView = new LogicCellView(new Point(arg0.getX(), arg0.getY()), gate);
		circuit.addGate(gate);
		circuitView.addGateView(gateView);
		editor.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
