package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.SevenSegmentsIndicatorView;

public class IndicatorCreator extends AbstractCircuitTool {
	private SevenSegmentsIndicatorView indicatorView;
	private boolean drawFantom;
	private boolean canPlace = true;
	
	public IndicatorCreator(CircuitEditor circuitEditor) {
		super(circuitEditor);
		indicatorView = new SevenSegmentsIndicatorView(new Point(), new SevenSegmentsIndicator());
		indicatorView.setFantom();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (canPlace) {
			Circuit circuit = circuitEditor.getCircuit();
			CircuitView circuitView = circuitEditor.getCircuitView();
			SevenSegmentsIndicator indicator = new SevenSegmentsIndicator();
			Point toPoint = new Point(arg0.getX() - indicatorView.getWidth(), arg0.getY() - indicatorView.getHeight());
			SevenSegmentsIndicatorView segmentsIndicatorView = new SevenSegmentsIndicatorView(toPoint, indicator);
			circuit.addElement(indicator);
			circuitView.addElementView(segmentsIndicatorView);
			circuitEditor.updateSize();
			circuitEditor.repaint();
			canPlace = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawFantom = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		drawFantom = false;
		circuitEditor.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point toPoint = new Point(arg0.getX() - indicatorView.getWidth(), arg0.getY() - indicatorView.getHeight());
		indicatorView.setPosition(toPoint);
		circuitEditor.repaint();
		if (arg0.getX() - indicatorView.getWidth() < 0 || arg0.getY() - indicatorView.getHeight() < 0) {
			canPlace = false;
			drawFantom = false;
		} else {
			canPlace = true;
			drawFantom = true;
		}
	}
	
	public void paint(Graphics g) {
		if (drawFantom) {
			indicatorView.paint(g);
		}
	}

}
