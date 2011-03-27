package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;

import com.gmail.gbmarkovsky.le.elements.Wire;

public class WireView {
	private Wire wire;
	private PinView start;
	private PinView end;
	
	public WireView(Wire wire, PinView start, PinView end) {
		this.wire = wire;
		this.start = start;
		this.end = end;
	}
	
	public Wire getWire() {
		return wire;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(start.getBorder().x, start.getBorder().y, end.getBorder().x, end.getBorder().y);
	}

	public PinView getStart() {
		return start;
	}

	public PinView getEnd() {
		return end;
	}
}
