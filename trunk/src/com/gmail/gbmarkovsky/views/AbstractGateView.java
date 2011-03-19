package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.gmail.gbmarkovsky.engine.Gate;
import com.gmail.gbmarkovsky.engine.Pin;

/**
 * Абстрактная реализация класса для отображения логического элемента.
 * @author george
 *
 */
public abstract class AbstractGateView implements GateView {
	protected Gate gate;
	protected Point position;
	protected int width = 30;
	protected int height = 45;
	protected ArrayList<PinView> inputs;
	protected PinView output;
	
	public AbstractGateView(Point position, Gate gate) {
		this.gate = gate;
		this.position = position;
		output = new PinView(new Point(position.x + width, position.y + height/2), gate.getOutput());
		inputs = new ArrayList<PinView>();
		ArrayList<Pin> pins = gate.getInputs();
		ArrayList<Point> inputPositions = getInputPositions();
		for (int i = 0; i < inputPositions.size(); i++) {
			inputs.add(new PinView(inputPositions.get(i), pins.get(i)));
		}
	}
	
	
	public ArrayList<Point> getInputPositions() {
		int inputCount = gate.getType().getInputCount();
		ArrayList<Point> list = new ArrayList<Point>(inputCount);
		for (int i = 1; i <= inputCount; i++) {
			list.add(new Point(position.x, position.y + i * height / (inputCount + 1)));
		}
		return list;
	}
	
	public Gate getGate() {
		return gate;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
		output.setPosition(new Point(position.x + width, position.y + height/2));
		ArrayList<Point> inputPositions = getInputPositions();
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).setPosition(inputPositions.get(i));
		}
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
	
	public boolean isPointInsideGateView(Point point) {
		int x = point.x;
		int y = point.y;
		int x0 = position.x;
		int y0 = position.y;
		int x1 = position.x + width;
		int y1 = position.y + height;
		if ((x <= x1) && (x >= x0) && (y <= y1) && (y >= y0)) {
			return true;
		}
		return false;
	}
	
	public abstract void paint(Graphics g);
}
