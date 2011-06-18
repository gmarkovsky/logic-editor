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
import java.util.List;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;

public class ElementDrugger extends AbstractCircuitTool {
	private Point prevPosition;
	private ElementView druggingElement;
	
	public ElementDrugger(CircuitEditor circuitEditor) {
		super(circuitEditor);
		prevPosition = new Point();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		druggingElement = circuitEditor.getCircuitView().getElementViewForLocation(arg0.getPoint());
		prevPosition = arg0.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		druggingElement = null;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (circuitEditor.getSelectedElements().contains(druggingElement)) {
			int dx = arg0.getX() - prevPosition.x;
			int dy = arg0.getY() - prevPosition.y;
			List<ElementView> list = circuitEditor.getSelectedElements();
			Point p = highLeftPoint(list);
			if (p.x + dx < 0 || p.y + dy < 0) {
				return;
			}
			for (ElementView ew: list) {
				Point oldPos = ew.getPosition();
				ew.setPosition(new Point(oldPos.x + dx, oldPos.y + dy));
			}
			circuitEditor.updateSize();
			prevPosition = arg0.getPoint();
			circuitEditor.repaint();
		}
	}

	private Point highLeftPoint(List<ElementView> list) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for(ElementView ew: list) {
			if (ew.getPosition().x < minX) {
				minX = ew.getPosition().x;
			}
			if (ew.getPosition().y < minY) {
				minY = ew.getPosition().y;
			}
		}
		return new Point(minX, minY);
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		prevPosition = arg0.getPoint();
	}
}
