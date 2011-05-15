package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;

public interface ElementView {
	public Point getPosition();
	public void setPosition(Point position);
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public PinView getPinViewForLocation(Point location);
	public boolean isPointInsideView(Point point);
	public boolean isInsideRect(Point p, int w, int h);
	public Element getElement();
	public PinView getInputPinView(Pin pin);
	public PinView getOutputPinView(Pin pin);
	public void setFantom();
	public void paint(Graphics g);
}
