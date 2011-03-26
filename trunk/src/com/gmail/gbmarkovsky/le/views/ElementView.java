package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;

public interface ElementView {
	public Point getPosition();
	public void setPosition(Point position);
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public PinView getPinViewForLocation(Point location);
	public boolean isPointInsideView(Point point);
	public void paint(Graphics g);
}
