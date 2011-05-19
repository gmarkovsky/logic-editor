package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
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
	private PinView startPin;
	private PinType waitForPinType;
	private WireView wireView;
	
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
				
				Pin start = startPin.getPin();

				
				Wire wire = null;
				
				
				
				if (start.getType() == PinType.OUTPUT) {
					Pin end = Pin.createInput(null);
					Point point = new Point(startPin.getCenter().x + PinView.PIN_WIDTH, startPin.getCenter().y);
					PinView pinV = new PinView(point, end);
					wire = new Wire(start, end);
					wireView = new WireView(wire, startPin, pinV);
				} else {
					Pin end = Pin.createOutput(null);
					Point point = new Point(startPin.getCenter().x - PinView.PIN_WIDTH, startPin.getCenter().y);
					PinView pinV = new PinView(point, end);
					wire = new Wire(end, start);
					wireView = new WireView(wire, pinV, startPin);
				}
				
				
				if (startPin.getPin().getType().equals(PinType.INPUT)) {
					waitForPinType = PinType.OUTPUT;
				} else {
					waitForPinType = PinType.INPUT;
				}
				circuitEditor.setDraggedSignal(startPin.getPin().getSignal());
			}
		} else {
			if (pinViewForLocation != null) {
				if (!pinViewForLocation.getPin().getType().equals(waitForPinType)) {
					return;
				}
				if (!pinViewForLocation.getPin().isNewConnectAllowed()) {
					return;
				}

				Pin end = pinViewForLocation.getPin();
				
				if (end.getType() == PinType.INPUT) {
					wireView.setEnd(pinViewForLocation);
				} else {
					wireView.setStart(pinViewForLocation);
				}
				
				circuitEditor.getCircuitView().addWireView(wireView);
				circuitEditor.getCircuit().addWire(wireView.getWire());
				startPin = null;
				wireView = null;
				circuitEditor.setDraggedSignal(null);
			} else {
				wireView.disconnect();
				circuitEditor.setDraggedSignal(null);
				startPin = null;
				wireView = null;
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
		if (wireView != null) {
			if (startPin.getPin().getType() == PinType.OUTPUT) {
				Pin end = Pin.createInput(null);
				Point point = new Point(arg0.getPoint().x + PinView.PIN_WIDTH, arg0.getPoint().y);
				PinView pinV = new PinView(point, end);
				wireView.setEnd(pinV);
			} else {
				Pin start = Pin.createOutput(null);

				Point point = new Point(arg0.getPoint().x - PinView.PIN_WIDTH, arg0.getPoint().y);
				PinView pinV = new PinView(point, start);
				wireView.setStart(pinV);
				PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(arg0.getPoint());
				if (pinViewForLocation != null) {
					if (pinViewForLocation.getPin().getType() == PinType.OUTPUT) {
						wireView.getWire().setSignal(pinViewForLocation.getPin().getSignal());
					}
				}
			}
		}
		
		circuitEditor.repaint();
	}

	public void paint(Graphics g) {
//		if (startPin != null) {
//			Graphics2D g2 = (Graphics2D) g;
//			if (startPin.getPin().getSignal() == Signal.TRUE) {
//				g2.setColor(new Color(147, 205, 90));
//			} else if (startPin.getPin().getSignal() == Signal.FALSE) {
//				g2.setColor(Color.gray);
//			}
//			Stroke tmpStroke = g2.getStroke();
//	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//	        BasicStroke stroke = new BasicStroke(3.0f);
//	        g2.setStroke(stroke);
//			g2.drawLine(startPin.getBorder().x, startPin.getBorder().y, mousePosition.x, mousePosition.y);
//			g2.setStroke(tmpStroke);
//		}
		
		if (wireView != null) {
			wireView.paint(g);
		}
	}
}
