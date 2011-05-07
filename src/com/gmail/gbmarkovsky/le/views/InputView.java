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
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class InputView implements ElementView {
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private Point position;
	private PinView outPin;
	private Input input;
	
	private int alpha = 255;
	
	public InputView(Point position, Input input) {
		this.input = input;
		this.outPin = new PinView(new Point(), this.input.getPin());
		setPosition(position);
	}
	
	public void setPosition(Point position) {
		this.position = position;
		outPin.setPosition(new Point(position.x + WIDTH + 1, position.y + HEIGHT/2));
	}
	
	public Input getInput() {
		return input;
	}

	/**
	 * Возвращает PinView включающий точку <code>location</code>.
	 * @param location точка
	 * @return PinView под точкой <code>location</code>, если такого нет, то null
	 */
	public PinView getPinViewForLocation(Point location) {
		if (outPin.isPointOnPin(location)) {
			return outPin;
		}
		return null;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		outPin.paint(g2);
		
		Stroke tmpStroke = g2.getStroke();
        BasicStroke stroke = new BasicStroke(3.0f);
        g2.setStroke(stroke);
        
        if (input.getSignal() == Signal.NONE) {
        	g2.setColor(new Color(255, 255, 255, alpha));
		} else if (input.getSignal() == Signal.TRUE) {
			g2.setColor(new Color(86, 193, 69));
		} else if (input.getSignal() == Signal.FALSE) {
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
		return input;
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
		throw new RuntimeException("Can't get input pin view for inputview");
	}

	@Override
	public PinView getOutput() {
		return outPin;
	}

	@Override
	public void setFantom() {
		alpha = 128;
		outPin.setFantom();
	}
	
	public void switchSignal() {
		if (input.getSignal() == Signal.NONE) {
			input.setSignal(Signal.TRUE);
		} else if (input.getSignal() == Signal.TRUE) {
			input.setSignal(Signal.FALSE);
		} else if (input.getSignal() == Signal.FALSE) {
			input.setSignal(Signal.TRUE);
		}
	}
}
