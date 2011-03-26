package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Output;

public class OutputView {
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
}
