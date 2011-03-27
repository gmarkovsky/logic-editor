package com.gmail.gbmarkovsky.le.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.WireView;

public class WireSelectionTool implements CircuitTool {
	private CircuitEditor circuitEditor;
	private WireView selectedWire;

	public WireSelectionTool(CircuitEditor circuitEditor) {
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
		Point m = arg0.getPoint();
		for (WireView wv: circuitEditor.getCircuitView().getWires()) {
			Point p = wv.getStart().getBorder();
			Point q = wv.getEnd().getBorder();
			double y = 0;
			if (p.x - q.x != 0) {
				y = (p.y - q.y)/(p.x - q.x) * m.x - q.x/(p.x - q.x) + q.y;
			} else {
				y = 0;
			}
			if (Math.abs(y - m.y) < 1) {
				selectedWire = wv;
				break;
			}
		}
		circuitEditor.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
//		Point pressPosition = arg0.getPoint();
//		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
//		if (pinViewForLocation != null) {
//			markedPin = pinViewForLocation;
//		} else {
//			markedPin = null;
//		}
//		circuitEditor.repaint();
	}

	public void paint(Graphics g) {
		if (selectedWire != null) {
			g.setColor(Color.red);
			Point p = selectedWire.getStart().getBorder();
			Point q = selectedWire.getEnd().getBorder();
			g.drawLine(p.x, p.y, q.x, q.y);
		}
	}
}
