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

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.PinType;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.PinView;
import com.gmail.gbmarkovsky.le.views.WireView;

public class WireChanger extends AbstractCircuitTool {
	private PinView donorPin;
	private PinType waitForPinType;
	private WireView wireView;
	
	public WireChanger(CircuitEditor circuitEditor) {
		super(circuitEditor);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point pressPosition = arg0.getPoint();
		PinView pinViewForLocation = circuitEditor.getCircuitView().getPinViewForLocation(pressPosition);
		if (donorPin == null) {
			if (pinViewForLocation != null) {
				if (!pinViewForLocation.getPin().getWires().isEmpty()) {
					donorPin = pinViewForLocation;
					
					if (pinViewForLocation.isWireOnPin(circuitEditor.getSelectedWireView())) {
						wireView = circuitEditor.getSelectedWireView();
					} else {
						wireView = pinViewForLocation.getWires().get(0);
					}
					
					
					if (donorPin.getPin().getType() == PinType.INPUT) {
						Pin end = Pin.createInput(null);
						Point point = new Point(arg0.getPoint().x + PinView.PIN_WIDTH, arg0.getPoint().y);
						PinView pinV = new PinView(point, end);
						wireView.setEnd(pinV);
					} else {
						Pin start = Pin.createOutput(null);
						Point point = new Point(arg0.getPoint().x - PinView.PIN_WIDTH, arg0.getPoint().y);
						PinView pinV = new PinView(point, start);
						wireView.setStart(pinV);
					}
					
					
					if (donorPin.getPin().getType().equals(PinType.OUTPUT)) {
						waitForPinType = PinType.OUTPUT;
					} else {
						waitForPinType = PinType.INPUT;
					}
					circuitEditor.setDraggedSignal(wireView.getWire().getSignal());
					//circuitEditor.getCircuitView().deleteWire(wireView);
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

				Pin end = pinViewForLocation.getPin();
				
				if (end.getType() == PinType.OUTPUT) {
					wireView.setStart(pinViewForLocation);
				} else {
					wireView.setEnd(pinViewForLocation);
				}
				

				donorPin = null;
				wireView = null;
				circuitEditor.setDraggedSignal(null);
			} else {
				circuitEditor.getCircuitView().deleteWire(wireView);
				circuitEditor.setDraggedSignal(null);
				donorPin = null;
				wireView = null;
			}
		}
		circuitEditor.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (wireView != null) {
			if (donorPin.getPin().getType() == PinType.INPUT) {
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
}
