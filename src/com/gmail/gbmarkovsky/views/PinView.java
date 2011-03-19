package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.gmail.gbmarkovsky.engine.Pin;
import com.gmail.gbmarkovsky.engine.PinType;

public class PinView {
	private Point position;
	private Pin pin;
	
	public PinView(Point position, Pin pin) {
		this.position = position;
		this.pin = pin;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void paint(Graphics g) {
		int x = 0;
		int y = position.y;
		if (pin.getType().equals(PinType.INPUT)) {
			x = position.x - 8;
		} else {
			x = position.x + 8;
		}
		g.drawLine(position.x, position.y, x, y);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(x - 2, y - 2, 5, 5);
	}
}
