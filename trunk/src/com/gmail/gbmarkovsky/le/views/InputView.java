package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Pin;

public class InputView implements ElementView {
	private static final int HEIGHT = 16;
	private static final int WIDTH = 16;
	private Point position;
	private PinView outPin;
	private Input input;
	
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
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.fillOval(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawOval(position.x, position.y, WIDTH, HEIGHT);
		outPin.paint(g);
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
}
