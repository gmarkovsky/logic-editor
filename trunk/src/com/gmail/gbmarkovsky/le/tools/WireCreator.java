package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.Wire;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.PinView;
import com.gmail.gbmarkovsky.le.views.WireView;

public class WireCreator implements CircuitTool {
	private CircuitEditor circuitEditor;
	private Point mousePosition;
	private PinView startPin;
	
	public WireCreator(CircuitEditor circuitEditor) {
		this.circuitEditor = circuitEditor;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point pressPosition = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
		if (startPin == null) {
			if (pinViewForLocation != null) {
				startPin = pinViewForLocation;
			}
		} else {
			Pin start = startPin.getPin();
			Pin end = pinViewForLocation.getPin();
			Wire wire = new Wire(start, end);
			start.addWire(wire);
			end.addWire(wire);
			WireView wireView = new WireView(wire, startPin, pinViewForLocation);
			circuitEditor.getCircuitView().addWireView(wireView);
			circuitEditor.getCircuit().addWire(wire);
			startPin = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousePosition = arg0.getPoint();
		circuitEditor.repaint();
	}

	public void paint(Graphics g) {
		if (startPin != null) {
			g.drawLine(startPin.getBorder().x, startPin.getBorder().y, mousePosition.x, mousePosition.y);
		}
	}
}
