package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.PinType;
import com.gmail.gbmarkovsky.le.elements.Wire;
import com.gmail.gbmarkovsky.le.tools.ElementSelector;

public class WireView {
	private Wire wire;
	private PinView start;
	private PinView end;
	
	private boolean selected;
	
	//private Line2D line;
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
		//line = new Line2D.Double(start.getBorder().x, start.getBorder().y, end.getBorder().x, end.getBorder().y);
	}
	
	public Wire getWire() {
		return wire;
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
			xs[i] = p.getPoint().x;
			ys[i] = p.getPoint().y;
			i++;
		}
		
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//line = new Line2D.Double(start.getBorder().x, start.getBorder().y, end.getBorder().x, end.getBorder().y);
		Stroke tmpStroke = g2.getStroke();
        g2.setStroke(stroke);
		//g2.draw(line);
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

			Rectangle r = ElementSelector.normalizeRect(new Point(start.getBorder().x, start.getBorder().y),
					new Point(fractures.getFirst().getPoint().x,
							fractures.getFirst().getPoint().y));
			Rectangle2D rect = new Rectangle2D.Double(r.x, r.y, r.width, r.height);
			if (rect.contains(fractureView.getPoint())) {
				fractures.addFirst(fractureView);
				return;
			}
			
			r = ElementSelector.normalizeRect(new Point(fractures.getLast().getPoint().x,
					fractures.getLast().getPoint().y),
					new Point(end.getBorder().x, end.getBorder().y));
			rect = new Rectangle2D.Double(r.x, r.y, r.width, r.height);
			if (rect.contains(fractureView.getPoint())) {
				fractures.addLast(fractureView);
				return;
			}

			Object[] o = fractures.toArray();
			
			for (int i = 0; i < o.length - 1; i++) {
				FractureView f1 = (FractureView) o[i];
				FractureView f2 = (FractureView) o[i+1];
				
				r = ElementSelector.normalizeRect(new Point(f1.getPoint().x,
						f1.getPoint().y),
						new Point(f2.getPoint().x,
								f2.getPoint().y));
				rect = new Rectangle2D.Double(r.x, r.y, r.width, r.height);
				if (rect.contains(fractureView.getPoint())) {
					fractures.add(i, fractureView);
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
			xs[i] = p.getPoint().x;
			ys[i] = p.getPoint().y;
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
			if (p.contsins(point)) {
				return true;
			}
		}
		return false;
	}
	
	public FractureView getFractureForLocation(Point point) {
		for (FractureView p : fractures) {
			if (p.contsins(point)) {
				return p;
			}
		}
		return null;
	}
	
	public void disconnect() {
		start.removeWireView(this);
		end.removeWireView(this);
	}
}
