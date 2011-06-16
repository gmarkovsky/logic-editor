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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.GateType;
import com.gmail.gbmarkovsky.le.elements.Pin;

/**
 * Интерфейс для всех классов, отображающих логические элементы схемы.
 * @author george
 *
 */
public abstract class AbstractGateView implements ElementView {
	protected Gate gate;
	/**
	 * Левый верхний угол гейта
	 */
	protected Point position;
	protected int width = 30;
	protected int height = 40;
	protected ArrayList<PinView> inputs;
	protected PinView output;

	private boolean selected;
	
	private boolean readOnly;
	
	private int alpha = 255;

	public AbstractGateView(Point position, Gate gate, int width, int height) {
		this.gate = gate;
		this.position = position;
		this.width = width;
		this.height = height;
		output = new PinView(new Point(position.x + width + 1, position.y + height/2), gate.getOutputs().get(0));
		inputs = new ArrayList<PinView>();
		List<Pin> pins = gate.getInputs();
		List<Point> inputPositions = getInputPositions();
		for (int i = 0; i < inputPositions.size(); i++) {
			inputs.add(new PinView(inputPositions.get(i), pins.get(i)));
		}
	}
	
	public static AbstractGateView createFor(Point position, Gate gate) {
		if (gate.getType().equals(GateType.YES) || gate.getType().equals(GateType.NOT)) {
			return new SmallGateView(position, gate);
		} else {
			return new GateView(position, gate);
		}
	}
	
	public List<Point> getInputPositions() {
		int inputCount = gate.getType().getInputCount();
		ArrayList<Point> list = new ArrayList<Point>(inputCount);
		for (int i = 1; i <= inputCount; i++) {
			list.add(new Point(position.x, position.y + i * height / (inputCount + 1)));
		}
		return list;
	}
	
	public Gate getGate() {
		return gate;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
		output.setPosition(new Point(position.x + width + 1, position.y + height/2));
		List<Point> inputPositions = getInputPositions();
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).setPosition(inputPositions.get(i));
		}
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isPointInsideView(Point point) {
		int x = point.x;
		int y = point.y;
		int x0 = position.x;
		int y0 = position.y;
		int x1 = position.x + width;
		int y1 = position.y + height;
		if ((x <= x1) && (x >= x0) && (y <= y1) && (y >= y0)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Возвращает PinView включающий точку <code>location</code>.
	 * @param location точка
	 * @return PinView под точкой <code>location</code>, если такого нет, то null
	 */
	public PinView getPinViewForLocation(Point location) {
		if (output.isPointOnPin(location)) {
			return output;
		}
		for (PinView pv: inputs) {
			if (pv.isPointOnPin(location)) {
				return pv;
			}
		}
		return null;
	}
	
	public boolean isInsideRect(Point p, int w, int h) {
		if ((position.x - p.x < w - width) && (position.x > p.x) &&
				(position.y - p.y < h - height) && (position.y > p.y)) {
			return true;
		}
		return false;
	}
	
	public PinView getInputPinView(Pin pin) {
		for(PinView pw: inputs) {
			if (pw.getPin() == pin) {
				return pw;
			}
		}
		return null;
	}
	
	public PinView getOutput() {
		return output;
	}

	public void setFantom() {
		alpha = 128;
		output.setFantom();
		for (PinView pv: inputs) {
			pv.setFantom();
		}
	}
	
	@Override
	public void paint(Graphics g) {	
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(new Color(255, 255, 255, alpha));
		g2.fillRect(position.x, position.y, width, height);
		
		output.paint(g2);
		for (PinView pv: inputs) {
			pv.paint(g2);
		}
		
		g2.setColor(new Color(0, 0, 0, alpha));
		Stroke tmpStroke = g2.getStroke();
		Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);
		g2.draw(new Rectangle2D.Double(position.x, position.y, width, height));
		g2.setStroke(tmpStroke);
		
		g2.setColor(new Color(0, 0, 0, alpha));
		
		Font tmpFont = g2.getFont();
		String symbol = gate.getType().getSymbol();
		
		g2.setFont(new Font(tmpFont.getFontName(), tmpFont.getStyle(), 14));
		
		Rectangle2D r = g2.getFontMetrics().getStringBounds(symbol, g2);
		
		int xTextPos = position.x + (int)(width - r.getWidth())/2;
		int yTextPos = position.y + (int)(height + r.getHeight()/2)/2;
		g2.drawString(symbol, xTextPos,  yTextPos);
		
		g2.setFont(tmpFont);
		
		if (selected) {
			g2.setColor(Color.red);
			g2.draw(new Rectangle2D.Double(position.x, position.y, width, height));
		}
	}

	@Override
	public Element getElement() {
		return gate;
	}

	@Override
	public PinView getOutputPinView(Pin pin) {
		if (output.getPin() == pin) {
			return output;
		}
		return null;
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
