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
	}
}
