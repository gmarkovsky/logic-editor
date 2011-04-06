package com.gmail.gbmarkovsky.le.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;
import com.gmail.gbmarkovsky.le.views.InputView;
import com.gmail.gbmarkovsky.le.views.OutputView;

public class ElementSelector implements CircuitTool {
	private CircuitEditor circuitEditor;
	private Point basePoint;
	private Point currentPoint;
	
	public ElementSelector(CircuitEditor circuitEditor) {
		super();
		this.circuitEditor = circuitEditor;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		ElementView selectedElement = circuitEditor.getCircuitView().getElementViewForLocation(arg0.getPoint());
		if (selectedElement != null && !circuitEditor.getSelectedElements().contains(selectedElement) ) {
			circuitEditor.setSelectedElement(selectedElement);
		}
		if (selectedElement == null) {
			basePoint = arg0.getPoint();
			currentPoint = basePoint;
			//circuitEditor.setSelection(true);
		}
		if (selectedElement == null) {
			circuitEditor.getSelectedElements().clear();
		}
		circuitEditor.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		basePoint = null;
		currentPoint = null;
		circuitEditor.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (basePoint != null) {
			currentPoint = e.getPoint();
			List<ElementView> list = circuitEditor.getSelectedElements();
			Rectangle rect = normalizeRect(basePoint, currentPoint);
			list.clear();
			list.addAll(circuitEditor.getCircuitView().getElementsInsideRect(rect.getLocation(), rect.width, rect.height));
			circuitEditor.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	public void paint(Graphics g) {
		List<ElementView> selectedElements = circuitEditor.getSelectedElements();
		for (ElementView selectedElement: selectedElements) {
			g.setColor(Color.red);
			if (selectedElement instanceof InputView || selectedElement instanceof OutputView) {
				g.drawOval(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
						selectedElement.getWidth() + 2, selectedElement.getHeight() + 2);
			} else {
				g.drawRect(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
						selectedElement.getWidth() + 2, selectedElement.getHeight() + 2);
			}
		}
		if (basePoint != null) {
			Rectangle rect = normalizeRect(basePoint, currentPoint);
			g.setColor(Color.blue);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	private Rectangle normalizeRect(Point basePoint, Point currentPoint) {
		Point p;
		int w;
		int h;
		Rectangle rectangle = null;
		int dx = basePoint.x - currentPoint.x;
		int dy = basePoint.y - currentPoint.y;
		if (dx > 0) {
			if (dy > 0) {
				p = currentPoint;
				w = dx;
				h = dy;
				rectangle = new Rectangle(p.x, p.y, w, h);
			} else {
				p = new Point(currentPoint.x, basePoint.y);
				w = dx;
				h = -dy;
				rectangle = new Rectangle(p.x, p.y, w, h);
			} 
		} else {
			if (dy > 0) {
				p = new Point(basePoint.x, currentPoint.y);
				w = -dx;
				h = dy;
				rectangle = new Rectangle(p.x, p.y, w, h);
			} else {
				p = basePoint;
				w = -dx;
				h = -dy;
				rectangle = new Rectangle(p.x, p.y, w, h);
			} 	
		}
		return rectangle;
	}
}
