package com.gmail.gbmarkovsky.engine;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.gui.CircuitEditor;
import com.gmail.gbmarkovsky.views.GateView;

public class GateDrugger implements CircuitTool {
	private CircuitEditor editor;
	private Point prevPosition;
	
	public GateDrugger(CircuitEditor editor) {
		this.editor = editor;
		prevPosition = new Point();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		prevPosition = arg0.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		GateView gateView = editor.getCircuitView().getGateViewForLocation(arg0.getPoint());
		int dx = arg0.getX() - prevPosition.x;
		int dy = arg0.getY() - prevPosition.y;
		if (gateView != null) {
			Point oldPos = gateView.getPosition();
			gateView.setPosition(new Point(oldPos.x + dx, oldPos.y + dy));
		}
		prevPosition = arg0.getPoint();
		editor.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		prevPosition = arg0.getPoint();
	}
}
