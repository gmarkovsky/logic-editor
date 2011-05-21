/*
 * Copyright (c) 2011, Georgy Markovsky 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 *  * Neither the name of  nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
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
import com.gmail.gbmarkovsky.le.views.WireView;

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
	public void mousePressed(MouseEvent event) {
		Point point = event.getPoint();
		
		ElementView selectedElement = circuitEditor.getCircuitView().getElementViewForLocation(point);
		WireView selectedWire = circuitEditor.getCircuitView().getWireViewForLocation(point);
		
		if (selectedElement == null) {
			if (selectedWire == null) {
				basePoint = point;
				currentPoint = basePoint;
				circuitEditor.clearSelection();
			} else {
				circuitEditor.clearSelection();
				circuitEditor.setSelectedWireView(selectedWire);
			}
		} else {
			if (selectedWire == null) {
				if (!circuitEditor.getSelectedElements().contains(selectedElement) ) {
					circuitEditor.clearSelection();
					circuitEditor.setSelectedElement(selectedElement);
				}
			} else {
				circuitEditor.setSelectedWireView(selectedWire);
			}
		}
		
		
		
		
		if (selectedElement == null) {
			basePoint = point;
			currentPoint = basePoint;
			circuitEditor.clearSelection();
		}
		
		circuitEditor.setSelectedWireView(null);
		
		if (selectedWire != null) {
			circuitEditor.setSelectedWireView(selectedWire);
			return;
		}
		
		if (selectedElement == null && selectedWire == null) {
			circuitEditor.clearSelection();
			return;
		}
		
		if (selectedElement != null && !circuitEditor.getSelectedElements().contains(selectedElement) ) {
			circuitEditor.setSelectedElement(selectedElement);
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
			Rectangle rect = normalizeRect(basePoint, currentPoint);
			circuitEditor.clearSelection();
			List<ElementView> elementsInsideRect = circuitEditor.getCircuitView().getElementsInsideRect(rect.getLocation(), rect.width, rect.height);
			circuitEditor.appearSelection(elementsInsideRect);
			circuitEditor.repaint();
		}
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
