package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.gmail.gbmarkovsky.engine.Pin;
import com.gmail.gbmarkovsky.engine.PinType;

public class PinView {
	private static final int PIN_WIDTH = 6;
	private static final int PIN_HEIGHT = 4;
	private Point position;
	private Point point;
	private Pin pin;
	
	public PinView(Point position, Pin pin) {
		this.pin = pin;
		setPosition(position);
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
		if (pin.getType().equals(PinType.INPUT)) {
			point = new Point(position.x - PIN_WIDTH/2, position.y);
		} else {
			point = new Point(position.x + PIN_WIDTH/2, position.y);
		}
	}

	public boolean isPointOnPin(Point p) {
		if ((p.x >= point.y - PIN_WIDTH/2) && (p.x <= point.y + PIN_WIDTH/2) &&
				(p.y >= point.y - PIN_HEIGHT/2) && (p.x <= point.y - PIN_HEIGHT/2)) {
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		//g.drawLine(position.x, position.y, point.x, point.y);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g.fillOval(point.x - 2, point.y - 2, PIN_DIAMETER, PIN_DIAMETER);
		g.fillRect(point.x - PIN_WIDTH/2, point.y - PIN_HEIGHT/2, PIN_WIDTH, PIN_HEIGHT);
	}
}
