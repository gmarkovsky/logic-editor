package com.gmail.gbmarkovsky.le.engine;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.PinView;

public class WireCreator implements CircuitTool {
	private CircuitEditor circuitEditor;
	@SuppressWarnings("unused")
	private Point mousePosition;
	
	public WireCreator(CircuitEditor circuitEditor) {
		this.circuitEditor = circuitEditor;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		Point pressPosition = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
		if (pinViewForLocation != null) {
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousePosition = arg0.getPoint();
	}

	public void paint(Graphics g) {
		//if (startPin != null) {
		//	g.drawLine(startPin.getPosition().x, startPin.getPosition().y, mousePosition.x, mousePosition.y);
		//}
	}
}
