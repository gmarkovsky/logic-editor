package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Gate;

/**
 * Класс для отображения логического элемента.
 * @author george
 *
 */
public class LogicCellView extends AbstractGateView {

	public LogicCellView(Point position, Gate gate) {
		super(position, gate);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(position.x, position.y, width, height);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, width, height);
		g.drawString(gate.getType().getSymbol(), position.x + 5,  position.y + 12);
		output.paint(g);
		for (PinView pv: inputs) {
			pv.paint(g);
		}
	}

	@Override
	public Element getElement() {
		return gate;
	}
}
