package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import com.gmail.gbmarkovsky.le.circuit.Signal;

public class FractureView {
	private static final int WIDTH = 8;
	private static final int HEIGHT = 8;
	
	private  WireView wireView;
	private Point point;

	public FractureView(Point point, WireView wireView) {
		super();
		this.point = point;
		this.wireView = wireView;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke tmpStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2.0f));
        Ellipse2D ellipse = buildEllipse();
        if (wireView.isSelected()) {
        	g2.setColor(new Color(250, 75, 75));
        } else
        if (wireView.getWire().getSignal().equals(Signal.TRUE)) {
        	g2.setColor(new Color(147, 205, 90));
        } else {
        	g2.setColor(Color.gray);
        }
        g2.fill(ellipse);
        g2.setColor(Color.black);
        g2.draw(ellipse);
        g2.setStroke(tmpStroke);
	}
	
	public boolean contsins(Point point) {
		Ellipse2D ellipse = buildEllipse();
		if (ellipse.contains(point)) {
			return true;
		}
		return false;
	}
	
	private Ellipse2D buildEllipse() {
		return new Ellipse2D.Double(this.point.x - WIDTH/2, this.point.y - HEIGHT/2, WIDTH, HEIGHT);
	}
}
