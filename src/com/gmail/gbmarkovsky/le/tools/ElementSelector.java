package com.gmail.gbmarkovsky.le.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;

public class ElementSelector extends AbstractCircuitTool {
	private Point basePoint;
	private Point currentPoint;
	
	public ElementSelector(CircuitEditor circuitEditor) {
		super(circuitEditor);
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
			clearSelected(circuitEditor.getSelectedElements());
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
			clearSelected(list);
			List<ElementView> elementsInsideRect = circuitEditor.getCircuitView().getElementsInsideRect(rect.getLocation(), rect.width, rect.height);
			setSelection(elementsInsideRect);
			list.addAll(elementsInsideRect);
			circuitEditor.repaint();
		}
	}

	private void setSelection(List<ElementView> list) {
		for(ElementView ev : list) {
			ev.setSelected(true);
		}
	}
	
	private void clearSelected(List<ElementView> list) {
		for(ElementView ev : list) {
			ev.setSelected(false);
		}
		list.clear();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
//		List<ElementView> selectedElements = circuitEditor.getSelectedElements();
//		for (ElementView selectedElement: selectedElements) {
//			g.setColor(new Color(250, 75, 75));
//			if (selectedElement instanceof ConnectorView) {
//				g2.drawOval(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
//						selectedElement.getWidth() + 2, selectedElement.getHeight() + 2);
//			} else {
//				g2.drawRect(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
//			}
//		}
		if (basePoint != null) {
			Rectangle rect = normalizeRect(basePoint, currentPoint);
			
			g2.setColor(new Color(134, 171, 217, 45));
			g2.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			g2.setColor(new Color(134, 171, 217));
			Stroke tmpStroke = g2.getStroke();
			Stroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2.setStroke(stroke);
			g2.draw(new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height));
			g2.setStroke(tmpStroke);
		}
	}
	
	public static Rectangle normalizeRect(Point basePoint, Point currentPoint) {
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
