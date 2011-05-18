package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class FractureView implements ElementView {
	private static final int WIDTH = 8;
	private static final int HEIGHT = 8;
	
	private  WireView wireView;
	private Point point;
	private boolean highlight;

	public FractureView(Point point, WireView wireView) {
		super();
		this.point = point;
		this.wireView = wireView;
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
        //g2.setColor(Color.black);
        //g2.draw(ellipse);
        g2.setStroke(tmpStroke);
        
        if (highlight) {
        	g2.setColor(Color.red);
        	g2.draw(ellipse);
        }
	}
	
	private Ellipse2D buildEllipse() {
		return new Ellipse2D.Double(this.point.x - WIDTH/2, this.point.y - HEIGHT/2, WIDTH, HEIGHT);
	}

	@Override
	public Point getPosition() {
		return point;
	}

	@Override
	public void setPosition(Point position) {
		this.point = position;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PinView getPinViewForLocation(Point location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPointInsideView(Point point) {
		Ellipse2D ellipse = buildEllipse();
		if (ellipse.contains(point)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInsideRect(Point p, int w, int h) {
		Rectangle2D rect = new Rectangle2D.Double(p.x, p.y, w, h);
		Ellipse2D ellipse = buildEllipse();
		return rect.contains(ellipse.getBounds2D());
	}

	@Override
	public Element getElement() {
		return null;
	}

	@Override
	public PinView getInputPinView(Pin pin) {
		return null;
	}

	@Override
	public PinView getOutputPinView(Pin pin) {
		return null;
	}

	@Override
	public void setFantom() {
		
	}

	@Override
	public void setSelected(boolean selected) {
		
	}
	
	public void setHighlighted(boolean highlight) {
		this.highlight = highlight;
	}
}
