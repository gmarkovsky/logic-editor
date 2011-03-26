package com.gmail.gbmarkovsky.le.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.PinView;

/**
 * Средство для выделения контакта при наведении на него мыши.
 * @author george
 *
 */
public class PinSelectionTool implements CircuitTool {
	private CircuitEditor circuitEditor;
	private PinView markedPin;

	public PinSelectionTool(CircuitEditor circuitEditor) {
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

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point pressPosition = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
		if (pinViewForLocation != null) {
			markedPin = pinViewForLocation;
		} else {
			markedPin = null;
		}
		circuitEditor.repaint();
	}

	public void paint(Graphics g) {
		if (markedPin != null) {
			Point p = markedPin.getCenter();
			g.setColor(Color.red);
			g.drawRect(p.x - PinView.PIN_WIDTH/2 - 1, p.y - PinView.PIN_HEIGHT/2 - 1,
					PinView.PIN_WIDTH + 1, PinView.PIN_HEIGHT + 1);
		}
	}
}
