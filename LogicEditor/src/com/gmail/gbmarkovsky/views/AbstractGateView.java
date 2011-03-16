package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.engine.Gate;

/**
 * Абстрактная реализация класса для отображения логического элемента.
 * @author george
 *
 */
public abstract class AbstractGateView implements GateView {
	protected Gate gate;
	protected Point position;
	protected int width = 40;
	protected int height = 60;
	
	public AbstractGateView(Point position, Gate gate) {
		this.gate = gate;
		this.position = position;
	}
	
	public Gate getGate() {
		return gate;
	}
	
	public Point getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public abstract void paint(Graphics g);
}
