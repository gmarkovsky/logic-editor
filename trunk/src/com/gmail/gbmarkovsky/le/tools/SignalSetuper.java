package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;
import com.gmail.gbmarkovsky.le.views.InputView;

public class SignalSetuper extends AbstractCircuitTool {

	public SignalSetuper(CircuitEditor circuitEditor) {
		super(circuitEditor);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		ElementView elementView = circuitEditor.getCircuitView().getElementViewForLocation(p);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (elementView instanceof InputView) {
				((InputView) elementView).switchSignal();
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			((InputView) elementView).getInput().setSignal(Signal.NONE);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
