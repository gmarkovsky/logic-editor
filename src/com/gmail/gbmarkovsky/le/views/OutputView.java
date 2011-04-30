package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class OutputView implements ElementView {
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private Point position;
	private PinView inPin;
	private Output output;
	
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
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.fillOval(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawOval(position.x, position.y, WIDTH, HEIGHT);
		inPin.paint(g);
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
}
