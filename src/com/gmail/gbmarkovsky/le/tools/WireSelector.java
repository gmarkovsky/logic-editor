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

public class WireSelector implements CircuitTool {
	private CircuitEditor circuitEditor;

	public WireSelector(CircuitEditor circuitEditor) {
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
		Point point = arg0.getPoint();
		for (WireView wv: circuitEditor.getCircuitView().getWires()) {
			if (wv.isPointOnWire(point)) {
				circuitEditor.setSelectedWireView(wv);
				circuitEditor.repaint();
				return;
			}
		}
		circuitEditor.setSelectedWireView(null);
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

	}

	public void paint(Graphics g) {
		WireView selectedWire = circuitEditor.getSelectedWireView();
		if (selectedWire != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(250, 75, 75));
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
