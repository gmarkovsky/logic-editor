package com.gmail.gbmarkovsky.le.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.PinType;
import com.gmail.gbmarkovsky.le.elements.Wire;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.PinView;
import com.gmail.gbmarkovsky.le.views.WireView;

/** 
 * Инструмент для соединения контактов проводниками.
 * @author george
 *
 */
public class WireCreator extends AbstractCircuitTool {
	private Point mousePosition;
	private PinView startPin;
	private PinType waitForPinType;
	
	public WireCreator(CircuitEditor circuitEditor) {
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
		Point pressPosition = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
		if (startPin == null) {
			if (pinViewForLocation != null) {
				if (!pinViewForLocation.getPin().isNewConnectAllowed()) {
					return;
				}
				startPin = pinViewForLocation;
				if (startPin.getPin().getType().equals(PinType.INPUT)) {
					waitForPinType = PinType.OUTPUT;
				} else {
					waitForPinType = PinType.INPUT;
				}
			}
		} else {
			if (pinViewForLocation != null) {
				if (!pinViewForLocation.getPin().getType().equals(waitForPinType)) {
					return;
				}
				if (!pinViewForLocation.getPin().isNewConnectAllowed()) {
					return;
				}
				Pin start = startPin.getPin();
				Pin end = pinViewForLocation.getPin();
				Wire wire = null;
				WireView wireView = null;
				if (start.getType() == PinType.OUTPUT && end.getType() == PinType.INPUT) {
					wire = new Wire(start, end);
					start.addWire(wire);
					end.addWire(wire);
					wireView = new WireView(wire, startPin, pinViewForLocation);
				} else {
					wire = new Wire(end, start);
					start.addWire(wire);
					end.addWire(wire);
					wireView = new WireView(wire, pinViewForLocation, startPin);
				}
				circuitEditor.getCircuitView().addWireView(wireView);
				circuitEditor.getCircuit().addWire(wire);
				startPin = null;
			} else {
				startPin = null;
			}
		}
		circuitEditor.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mousePosition = arg0.getPoint();
		circuitEditor.repaint();
	}

	public void paint(Graphics g) {
		if (startPin != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.black);
			Stroke tmpStroke = g2.getStroke();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        BasicStroke stroke = new BasicStroke(2.0f);
	        g2.setStroke(stroke);
			g2.drawLine(startPin.getBorder().x, startPin.getBorder().y, mousePosition.x, mousePosition.y);
			g2.setStroke(tmpStroke);
		}
	}
}
