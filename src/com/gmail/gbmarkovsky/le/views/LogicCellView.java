package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Gate;

/**
 * Класс для отображения логического элемента.
 * @author george
 *
 */
public class LogicCellView extends AbstractGateView {

	private int alpha = 255;
	
	public LogicCellView(Point position, Gate gate) {
		super(position, gate);
	}

	public void setFantom() {
		alpha = 128;
		output.setFantom();
		for (PinView pv: inputs) {
			pv.setFantom();
		}
	}
	
	@Override
	public void paint(Graphics g) {	
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//g2.setColor(Color.white);
		g2.setColor(new Color(255, 255, 255, alpha));
		g2.fillRect(position.x, position.y, width, height);
		
		output.paint(g2);
		for (PinView pv: inputs) {
			pv.paint(g2);
		}
		
		g2.setColor(new Color(0, 0, 0, alpha));
		Stroke tmpStroke = g2.getStroke();
		Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);
		g2.draw(new Rectangle2D.Double(position.x, position.y, width, height));
		g2.setStroke(tmpStroke);
		
		g2.setColor(new Color(0, 0, 0, alpha));
		g2.drawString(gate.getType().getSymbol(), position.x + 6,  position.y + 16);
	}

	@Override
	public Element getElement() {
		return gate;
	}
}
