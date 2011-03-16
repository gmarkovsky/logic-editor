package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.engine.Gate;

/**
 * Интерфейс для всех классов, отображающих логические элементы схемы.
 * @author george
 *
 */
public interface GateView {

	public Gate getGate();
	public Point getPosition();
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public void paint(Graphics g);
}
