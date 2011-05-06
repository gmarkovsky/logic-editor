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
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class OutputView implements ElementView {
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private Point position;
	private PinView inPin;
	private Output output;
	
	private int alpha = 255;
	
	public OutputView(Point position, Output output) {
		this.output = output;
		this.inPin = new PinView(new Point(), this.output.getPin());
		setPosition(position);
	}
	
	public void setPosition(Point position) {
		this.position = position;
		inPin.setPosition(new Point(position.x, position.y + HEIGHT/2));
	}
	
	public PinView getOutput() {
		return null;
	}
	
	public Output getOutputE() {
		return output;
	}
	
	/**
	 * Возвращает PinView включающий точку <code>location</code>.
	 * @param location точка
	 * @return PinView под точкой <code>location</code>, если такого нет, то null
	 */
	public PinView getPinViewForLocation(Point location) {
		if (inPin.isPointOnPin(location)) {
			return inPin;
		}
		return null;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		inPin.paint(g);
		
		Stroke tmpStroke = g2.getStroke();
        BasicStroke stroke = new BasicStroke(3.0f);
        g2.setStroke(stroke);
        if (output.getSignal() == Signal.NONE) {
        	g2.setColor(new Color(255, 255, 255, alpha));
		} else if (output.getSignal() == Signal.TRUE) {
			g2.setColor(new Color(86, 193, 69));
		} else if (output.getSignal() == Signal.FALSE) {
			g2.setColor(new Color(66, 76, 171));
		}
		g2.fill(new Ellipse2D.Double(position.x, position.y, WIDTH, HEIGHT)  );
		g2.setColor(new Color(0, 0, 0, alpha));
		g2.draw(new Ellipse2D.Double(position.x, position.y, WIDTH, HEIGHT));
		g2.setStroke(tmpStroke);
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
		return output;
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
		if (pin == inPin.getPin()) {
			return inPin;
		}
		return null;
	}

	@Override
	public void setFantom() {
		alpha = 128;
		inPin.setFantom();
	}
}
