package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.gmail.gbmarkovsky.le.elements.Wire;

public class WireView {
	private Wire wire;
	private PinView start;
	private PinView end;
	
	private Line2D line;
	private Stroke stroke = new BasicStroke(2.0f);
	
	public WireView(Wire wire, PinView start, PinView end) {
		this.wire = wire;
		this.start = start;
		this.end = end;
		line = new Line2D.Double(start.getBorder().x, start.getBorder().y, end.getBorder().x, end.getBorder().y);
	}
	
	public Wire getWire() {
		return wire;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		line = new Line2D.Double(start.getBorder().x, start.getBorder().y, end.getBorder().x, end.getBorder().y);
		Stroke tmpStroke = g2.getStroke();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(stroke);
		g2.draw(line);
		g2.setStroke(tmpStroke);
	}

	public PinView getStart() {
		return start;
	}

	public PinView getEnd() {
		return end;
	}
	
	public boolean isPointOnWire(Point point) {
		if (line.ptSegDist(point) < 3) {
			return true;
		}
		return false;
	}
}
