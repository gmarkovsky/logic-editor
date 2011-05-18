package com.gmail.gbmarkovsky.le.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.PinType;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.FractureView;
import com.gmail.gbmarkovsky.le.views.PinView;

/**
 * Средство для выделения контакта при наведении на него мыши.
 * @author george
 *
 */
public class PinSelector extends AbstractCircuitTool {
	private PinView markedPin;
	private FractureView markedFracture;

	public PinSelector(CircuitEditor circuitEditor) {
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

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point position = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(position);
		if (pinViewForLocation != null) {
			setPinHighlight(pinViewForLocation);
			if (circuitEditor.getDraggedSignal() != null && 
					markedPin.getPin().getType() == PinType.INPUT &&
					markedPin.getPin().isNewConnectAllowed()) {
				markedPin.getPin().setSignal(circuitEditor.getDraggedSignal());
			}
		} else {
			if (markedPin != null) {	
				if (circuitEditor.getDraggedSignal() != null &&
					markedPin.getPin().getType() == PinType.INPUT) {
					markedPin.getPin().setSignal(Signal.FALSE);
				}
			}
			resetPinHighlight();
			FractureView fractViewLoc = circuitEditor.getCircuitView().getFractureViewForLocation(position);
			if (fractViewLoc != null) {
				setFractureHighlight(fractViewLoc);
			} else {
				resetFractureHighlight();
			}
		}
		circuitEditor.repaint();
	}

	private void setPinHighlight(PinView pinView) {
		pinView.setHighlighted(true);
		markedPin = pinView;
	}
	
	private void resetPinHighlight() {
		if (markedPin != null) {
			markedPin.setHighlighted(false);
		}
		markedPin = null;
	}
	
	private void setFractureHighlight(FractureView fractureView) {
		fractureView.setHighlighted(true);
		markedFracture = fractureView;
	}
	
	private void resetFractureHighlight() {
		if (markedFracture != null) {
			markedFracture.setHighlighted(false);
		}
		markedFracture = null;
	}
	
	public void paint(Graphics g) {
		if (markedPin != null) {
			Point p = markedPin.getCenter();
			
			g.setColor(new Color(250, 75, 75));
			g.drawRect(p.x - PinView.PIN_WIDTH/2 - 1, p.y - PinView.PIN_HEIGHT/2 - 1,
					PinView.PIN_WIDTH + 1, PinView.PIN_HEIGHT + 1);
		}
		if (markedFracture != null) {
			
		}
	}
}
