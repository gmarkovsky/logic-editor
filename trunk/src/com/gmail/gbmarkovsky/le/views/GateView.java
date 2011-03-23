package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.engine.Gate;

/**
 * Интерфейс для всех классов, отображающих логические элементы схемы.
 * @author george
 *
 */
public interface GateView {

	public Gate getGate();
	public Point getPosition();
	public void setPosition(Point position);
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public PinView getPinViewForLocation(Point location);
	public boolean isPointInsideGateView(Point point);
	public void paint(Graphics g);
}