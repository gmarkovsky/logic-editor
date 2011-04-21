package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;

public class ElementDrugger extends AbstractCircuitTool {
	private Point prevPosition;
	private ElementView druggingElement;
	
	public ElementDrugger(CircuitEditor circuitEditor) {
		super(circuitEditor);
		prevPosition = new Point();
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
		druggingElement = circuitEditor.getCircuitView().getElementViewForLocation(arg0.getPoint());
		prevPosition = arg0.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		druggingElement = null;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (circuitEditor.getSelectedElements().contains(druggingElement)) {
			int dx = arg0.getX() - prevPosition.x;
			int dy = arg0.getY() - prevPosition.y;
			List<ElementView> list = circuitEditor.getSelectedElements();
			for (ElementView ew: list) {
				Point oldPos = ew.getPosition();
				ew.setPosition(new Point(oldPos.x + dx, oldPos.y + dy));
			}
			circuitEditor.updateSize();
			prevPosition = arg0.getPoint();
			circuitEditor.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		prevPosition = arg0.getPoint();
	}

	@Override
	public void paint(Graphics g) {
		
	}
}
