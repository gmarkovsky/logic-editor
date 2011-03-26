package com.gmail.gbmarkovsky.le.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;

public class GateDrugger implements CircuitTool {
	private CircuitEditor editor;
	private Point prevPosition;
	private ElementView druggingElement;
	
	public GateDrugger(CircuitEditor editor) {
		this.editor = editor;
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
		druggingElement = editor.getCircuitView().getElementViewForLocation(arg0.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		druggingElement = null;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		int dx = arg0.getX() - prevPosition.x;
		int dy = arg0.getY() - prevPosition.y;
		if (druggingElement != null) {
			Point oldPos = druggingElement.getPosition();
			druggingElement.setPosition(new Point(oldPos.x + dx, oldPos.y + dy));
		}
		prevPosition = arg0.getPoint();
		editor.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		prevPosition = arg0.getPoint();
	}
}
