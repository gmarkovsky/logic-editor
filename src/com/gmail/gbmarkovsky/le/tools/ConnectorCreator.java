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

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Connector;
import com.gmail.gbmarkovsky.le.elements.Connector.ConnectorType;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.ConnectorView;

public class ConnectorCreator extends AbstractCircuitTool {
	private ConnectorView connectorView;
	private boolean drawFantom;
	private boolean canPlace = true;
	private ConnectorType connectorType;
	
	public ConnectorCreator(CircuitEditor circuitEditor, ConnectorType connectorType) {
		super(circuitEditor);
		this.connectorType = connectorType;
		Connector connector = new Connector(this.connectorType);
		connectorView = new ConnectorView(new Point(0, 0), connector);
		connectorView.setFantom();
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (canPlace) {
			Circuit circuit = circuitEditor.getCircuit();
			CircuitView circuitView = circuitEditor.getCircuitView();
			Connector connector = new Connector(this.connectorType);
			Point toPoint = new Point(arg0.getX() - connectorView.getWidth(), arg0.getY() - connectorView.getHeight());
			ConnectorView connectorView = new ConnectorView(toPoint, connector);
			if (this.connectorType.equals(ConnectorType.INPUT)) {
				circuit.addInput(connector);
				circuitView.addInputView(connectorView);
			} else if (this.connectorType.equals(ConnectorType.OUTPUT)) {
				circuit.addOutput(connector);
				circuitView.addOutputView(connectorView);
			}
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
		Point toPoint = new Point(arg0.getX() - connectorView.getWidth(), arg0.getY() - connectorView.getHeight());
		connectorView.setPosition(toPoint);
		circuitEditor.repaint();
		if (arg0.getX() - connectorView.getWidth() < 0 || arg0.getY() - connectorView.getHeight() < 0) {
			canPlace = false;
			drawFantom = false;
		} else {
			canPlace = true;
			drawFantom = true;
		}
	}

	@Override
	public void paint(Graphics g) {
		if (drawFantom) {
			connectorView.paint(g);
		}
	}

}
