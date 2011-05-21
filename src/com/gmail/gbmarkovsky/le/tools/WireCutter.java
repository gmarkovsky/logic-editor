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
			
			Point oldPos = draggedFracture.getPosition();
			draggedFracture.setPosition(new Point(oldPos.x + dx, oldPos.y + dy));
			
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
