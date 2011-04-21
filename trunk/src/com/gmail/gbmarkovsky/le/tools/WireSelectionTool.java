package com.gmail.gbmarkovsky.le.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
			if (wv.isPointOnWire(m)) {
				selectedWire = wv;
				circuitEditor.repaint();
				return;
			}
		}
		selectedWire = null;
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
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.red);
			Stroke tmpStroke = g2.getStroke();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        BasicStroke stroke = new BasicStroke(2.0f);
	        g2.setStroke(stroke);
			Point p = selectedWire.getStart().getBorder();
			Point q = selectedWire.getEnd().getBorder();
			g2.drawLine(p.x, p.y, q.x, q.y);
			g2.setStroke(tmpStroke);
		}
	}
}
