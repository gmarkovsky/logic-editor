package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.FractureView;
import com.gmail.gbmarkovsky.le.views.WireView;

public class WireCutter extends AbstractCircuitTool {
	private Point cutPoint;
	private WireView cuttedWire;
	private FractureView draggedFracture;
	private Point prevPosition;
	
	public WireCutter(CircuitEditor circuitEditor) {
		super(circuitEditor);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point point = e.getPoint();
		if (e.getButton() == MouseEvent.BUTTON1) {
		
		for (WireView wv: circuitEditor.getCircuitView().getWires()) {
			if (wv.isPointOnWire(point)) {
				cutPoint = point;
				cuttedWire = wv;
				break;
			} else {
				cutPoint = null;
				cuttedWire = null;
			}

		}
		if (cuttedWire != null) {
			if (cuttedWire.isPointOnFracture(point)) {
				draggedFracture = cuttedWire.getFractureForLocation(point);
			}
			}
		} else {
			for (WireView wv: circuitEditor.getCircuitView().getWires()) {
				FractureView fractureView = wv.getFractureForLocation(point);
				if (fractureView != null) {
					wv.removeFracture(fractureView);
				}
			}
		}
		prevPosition = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		cutPoint = null;
		cuttedWire = null;
		draggedFracture = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draggedFracture != null) {
			int dx = e.getX() - prevPosition.x;
			int dy = e.getY() - prevPosition.y;
			
			Point oldPos = draggedFracture.getPoint();
			draggedFracture.setPoint(new Point(oldPos.x + dx, oldPos.y + dy));
			
			circuitEditor.updateSize();
			prevPosition = e.getPoint();
			circuitEditor.repaint();
		} else {
			if (cutPoint != null) {
				FractureView fractureView = new FractureView(cutPoint, cuttedWire);
				cuttedWire.addFracture(fractureView);
				draggedFracture = fractureView;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		prevPosition = e.getPoint();
	}

}
