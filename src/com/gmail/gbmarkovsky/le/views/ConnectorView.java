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
import com.gmail.gbmarkovsky.le.elements.Connector;
import com.gmail.gbmarkovsky.le.elements.Connector.ConnectorType;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class ConnectorView implements ElementView {
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private Point position;
	private PinView pin;
	private Connector connector;
	private boolean selected;
	
	private int alpha = 255;
	
	public ConnectorView(Point position, Connector connector) {
		this.connector = connector;
		if (connector.getType().equals(ConnectorType.INPUT)) {
			this.pin = new PinView(new Point(), this.connector.getOutput(0));
		} else if (connector.getType().equals(ConnectorType.OUTPUT)) {
			this.pin = new PinView(new Point(), this.connector.getInput(0));
		}
		setPosition(position);
	}
	
	public void setPosition(Point position) {
		this.position = position;
		if (connector.getType().equals(ConnectorType.INPUT)) {
			pin.setPosition(new Point(position.x + WIDTH + 1, position.y + HEIGHT/2));
		} else if (connector.getType().equals(ConnectorType.OUTPUT)) {
			pin.setPosition(new Point(position.x, position.y + HEIGHT/2));
		}
	}

	/**
	 * Возвращает PinView включающий точку <code>location</code>.
	 * @param location точка
	 * @return PinView под точкой <code>location</code>, если такого нет, то null
	 */
	public PinView getPinViewForLocation(Point location) {
		if (pin.isPointOnPin(location)) {
			return pin;
		}
		return null;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		pin.paint(g2);
		
		Stroke tmpStroke = g2.getStroke();
        BasicStroke stroke = new BasicStroke(3.0f);
        g2.setStroke(stroke);
        
        if (connector.getSignal() == Signal.TRUE) {
			g2.setColor(new Color(147, 205, 90));
		} else if (connector.getSignal() == Signal.FALSE) {
			g2.setColor(new Color(255, 255, 255, alpha));
		}
        
		g2.fill(new Ellipse2D.Double(position.x, position.y, WIDTH, HEIGHT)  );
		g2.setColor(new Color(0, 0, 0, alpha));
		g2.draw(new Ellipse2D.Double(position.x, position.y, WIDTH, HEIGHT));
		g2.setStroke(tmpStroke);
		
		if (selected) {
			g2.setColor(Color.red);
			g2.draw(new Ellipse2D.Double(position.x, position.y, WIDTH, HEIGHT));
		}
	}

	@Override
	public Point getPosition() {
		return position;
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
	public boolean isPointInsideView(Point point) {
		int x = point.x;
		int y = point.y;
		int x0 = position.x;
		int y0 = position.y;
		int x1 = position.x + WIDTH;
		int y1 = position.y + HEIGHT;
		if ((x <= x1) && (x >= x0) && (y <= y1) && (y >= y0)) {
			return true;
		}
		return false;
	}

	@Override
	public Element getElement() {
		return connector;
	}

	@Override
	public boolean isInsideRect(Point p, int w, int h) {
		if ((position.x - p.x < w - WIDTH) && (position.x > p.x) &&
				(position.y - p.y < h - HEIGHT) && (position.y > p.y)) {
			return true;
		}
		return false;
	}

	@Override
	public PinView getInputPinView(Pin pin) {
		return (connector.getType().equals(ConnectorType.OUTPUT)) ?this.pin:null;
		//throw new RuntimeException("Can't get input pin view for inputview");
	}

	public PinView getPin() {
		return pin;
	}

	@Override
	public void setFantom() {
		alpha = 128;
		pin.setFantom();
	}
	
	public void switchSignal() {
		if (connector.getSignal() == Signal.TRUE) {
			connector.setSignal(Signal.FALSE);
		} else if (connector.getSignal() == Signal.FALSE) {
			connector.setSignal(Signal.TRUE);
		}
	}

	public ConnectorType getType() {
		return connector.getType();
	}

	@Override
	public PinView getOutputPinView(Pin pin) {
		return (connector.getType().equals(ConnectorType.INPUT)) ?this.pin:null;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
