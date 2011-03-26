package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Input;

public class InputView {
	private static final int HEIGHT = 20;
	private static final int WIDTH = 20;
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
		g.setColor(Color.white);
		g.fillRect(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, WIDTH, HEIGHT);
		//g.drawString(gate.getType().getSymbol(), position.x + 5,  position.y + 12);
		outPin.paint(g);
	}
}
