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
package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.PinType;
import com.gmail.gbmarkovsky.le.elements.Wire;

public class WireView {
	private Wire wire;
	private PinView start;
	private PinView end;
	
	private boolean selected;

	private Stroke stroke = new BasicStroke(3.0f);

	private LinkedList<FractureView> fractures = new LinkedList<FractureView>();
	
	public WireView(Wire wire, PinView start, PinView end) {
		if (start.getPin().getType() != PinType.OUTPUT || end.getPin().getType() != PinType.INPUT) {
			throw new RuntimeException("Wrong wire connection");
		}
		this.wire = wire;
		this.start = start;
		this.end = end;
		this.start.addWireView(this);
		this.end.addWireView(this);
	}
	
	public Wire getWire() {
		return wire;
	}
	
	public void setStart(PinView pinView) {
		this.start.removeWireView(this);
		this.start = pinView;
		wire.setStart(pinView.getPin());
		this.start.addWireView(this);
	}
	
	public void setEnd(PinView pinView) {
		this.end.removeWireView(this);
		this.end = pinView;
		wire.setEnd(pinView.getPin());
		this.end.addWireView(this);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (selected) {
			g2.setColor(new Color(250, 75, 75));
		} else {
		if (wire.getSignal() == Signal.TRUE) {
			g2.setColor(new Color(147, 205, 90));
		} else if (wire.getSignal() == Signal.FALSE) {
			g2.setColor(Color.gray);
		}
		}
		
		int[] xs = new int[fractures.size() + 2];
		int[] ys = new int[fractures.size() + 2];
		
		xs[0] = start.getBorder().x;
		ys[0] = start.getBorder().y;
		
		xs[xs.length - 1] = end.getBorder().x;
		ys[xs.length - 1] = end.getBorder().y;
		
		int i = 1;
		for (FractureView p : fractures) {
			xs[i] = p.getPosition().x;
			ys[i] = p.getPosition().y;
			i++;
		}
		
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke tmpStroke = g2.getStroke();
        g2.setStroke(stroke);
		g2.drawPolyline(xs, ys, fractures.size() + 2);
		g2.setStroke(tmpStroke);
		for (FractureView f : fractures) {
			f.paint(g2);
		}
	}

	
	public LinkedList<FractureView> getFractures() {
		return fractures;
	}

	public PinView getStart() {
		return start;
	}

	public PinView getEnd() {
		return end;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void addLast(FractureView fractureView) {
		fractures.addLast(fractureView);
	}
	
	public void addFracture(FractureView fractureView) {
		if (fractures.isEmpty()) {
			fractures.add(fractureView);
		} else {
		
		Line2D line = new Line2D.Double(start.getBorder().x, start.getBorder().y, fractures.getFirst().getPosition().x,
				fractures.getFirst().getPosition().y);	
		if (line.ptSegDist(fractureView.getPosition()) < 3) {
			fractures.addFirst(fractureView);
			return;
		}
		
		line = new Line2D.Double(new Point(fractures.getLast().getPosition().x,
				fractures.getLast().getPosition().y),
				new Point(end.getBorder().x, end.getBorder().y));
		if (line.ptSegDist(fractureView.getPosition()) < 3) {
			fractures.addLast(fractureView);
			return;
		}

		Object[] o = fractures.toArray();
		
		for (int i = 0; i < o.length - 1; i++) {
			FractureView f1 = (FractureView) o[i];
			FractureView f2 = (FractureView) o[i+1];
			
			line = new Line2D.Double(new Point(f1.getPosition().x,
					f1.getPosition().y),
					new Point(f2.getPosition().x,
							f2.getPosition().y));
			if (line.ptSegDist(fractureView.getPosition()) < 3) {
				fractures.add(i+1, fractureView);
				return;
			}
		}

	}
	}
	
	public boolean removeFracture(FractureView fractureView) {
		return fractures.remove(fractureView);
	}
	
	public boolean isPointOnWire(Point point) {
		int[] xs = new int[fractures.size() + 2];
		int[] ys = new int[fractures.size() + 2];
		
		xs[0] = start.getBorder().x;
		ys[0] = start.getBorder().y;
		
		xs[xs.length - 1] = end.getBorder().x;
		ys[xs.length - 1] = end.getBorder().y;
		
		int i = 1;
		for (FractureView p : fractures) {
			xs[i] = p.getPosition().x;
			ys[i] = p.getPosition().y;
			i++;
		}
		for (int j = 0; j < ys.length - 1; j++) {
			Line2D line = new Line2D.Double(xs[j], ys[j], xs[j+1], ys[j+1]);
			if (line.ptSegDist(point) < 3) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isPointOnFracture(Point point) {
		for (FractureView p : fractures) {
			if (p.isPointInsideView(point)) {
				return true;
			}
		}
		return false;
	}
	
	public FractureView getFractureForLocation(Point point) {
		for (FractureView p : fractures) {
			if (p.isPointInsideView(point)) {
				return p;
			}
		}
		return null;
	}
	
	public List<FractureView> getFracturesInsideRect(Point p, int w, int h) {
		List<FractureView> resultList = new ArrayList<FractureView>();
		for (FractureView fv : fractures) {
			if (fv.isInsideRect(p, w, h)) {
				resultList.add(fv);
			}
		}
		return resultList;
	}
	
	public void disconnect() {
		start.removeWireView(this);
		end.removeWireView(this);
	}
}
