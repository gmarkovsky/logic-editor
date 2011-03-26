package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Output;

public class OutputView implements ElementView {
	private static final int HEIGHT = 20;
	private static final int WIDTH = 20;
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
	
	public Output getOutput() {
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
		g.setColor(Color.white);
		g.fillRect(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, WIDTH, HEIGHT);
		//g.drawString(gate.getType().getSymbol(), position.x + 5,  position.y + 12);
		inPin.paint(g);
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
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
}
