package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsDetector;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator.Segment;

public class SevenSegmentsDetectorView implements ElementView {
	private static final int WIDTH = 110;
	private static final int HEIGHT = 180;
	private static final int TOP_SEGMENT_INSET = 26;
	private static final int MEDIUM_SEGMENT_INSET = 20;
	private static final int BOTTOM_SEGMENT_INSET = 14;
	
	private Map<String, SegmentView> segments = new HashMap<String, SegmentView>();
	private ArrayList<PinView> outputs = new ArrayList<PinView>();
	
	private Point position;
	private SevenSegmentsDetector indicator;
	
	public SevenSegmentsDetectorView(Point position,
			SevenSegmentsDetector indicator) {
		this.position = position;
		this.indicator = indicator;
		calculatePositions();
		List<Point> outPos = getInputPositions();
		for (int i = 0; i < 7; i++) {
			outputs.add(new PinView(outPos.get(i), Pin.createOutput(null)));
		}
	}

	private List<Point> getInputPositions() {
		int inputCount = 7;
		ArrayList<Point> list = new ArrayList<Point>(inputCount);
		for (int i = 1; i <= inputCount; i++) {
			list.add(new Point(position.x + WIDTH + 1, position.y + i * HEIGHT / (inputCount + 1)));
		}
		return list;
	}
	
	private void calculatePositions() {
		Point tl = new Point(position.x + TOP_SEGMENT_INSET, position.y + 20);
		Point tr = new Point(position.x + WIDTH - BOTTOM_SEGMENT_INSET, position.y + 20);
		Point ml = new Point(position.x + MEDIUM_SEGMENT_INSET, position.y + HEIGHT/2);
		Point mr = new Point(position.x + WIDTH - MEDIUM_SEGMENT_INSET, position.y + HEIGHT/2);
		Point bl = new Point(position.x + BOTTOM_SEGMENT_INSET, position.y + HEIGHT - 20);
		Point br = new Point(position.x + WIDTH - TOP_SEGMENT_INSET, position.y + HEIGHT - 20);
		List<Segment> list = indicator.getSegments();
		segments.put("a", new SegmentView(new Point(tl.x + 5, tl.y), new Point(tr.x - 5, tr.y), list.get(0)));
		segments.put("b", new SegmentView(new Point(ml.x + 5, ml.y), new Point(mr.x - 5, mr.y), list.get(1)));
		segments.put("c", new SegmentView(new Point(bl.x + 5, bl.y), new Point(br.x - 5, br.y), list.get(2)));
		
		segments.put("d", new SegmentView(new Point(tl.x, tl.y + 5), new Point(ml.x, ml.y - 5), list.get(3)));
		segments.put("e", new SegmentView(new Point(ml.x, ml.y + 5), new Point(bl.x, bl.y - 5), list.get(4)));
		
		segments.put("f", new SegmentView(new Point(tr.x, tr.y + 5), new Point(mr.x, mr.y - 5), list.get(5)));
		segments.put("g", new SegmentView(new Point(mr.x, mr.y + 5), new Point(br.x, br.y - 5), list.get(6)));
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, WIDTH, HEIGHT);
		for (SegmentView segment: segments.values()) {
			segment.paint(g);
		}
		for (PinView pinView: outputs) {
			pinView.paint(g);
		}
	}

	@Override
	public Element getElement() {
		return indicator;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point position) {
		this.position = position;
		List<Point> inputPositions = getInputPositions();
		for (int i = 0; i < outputs.size(); i++) {
			outputs.get(i).setPosition(inputPositions.get(i));
		}
		segments.clear();
		calculatePositions();
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PinView getPinViewForLocation(Point location) {
		for (PinView pv: outputs) {
			if (pv.isPointOnPin(location)) {
				return pv;
			}
		}
		return null;
	}

	@Override
	public boolean isPointInsideView(Point point) {
		int x = point.x;
		int y = point.y;
		int x0 = position.x;
		int y0 = position.y;
		int x1 = position.x + WIDTH;
		int y1 = position.y + HEIGHT;
		if ((x <= x1) && (x >= x0) && (y <= y1) && (y >= y0)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInsideRect(Point p, int w, int h) {
		if ((position.x - p.x < w - WIDTH) && (position.x > p.x) &&
				(position.y - p.y < h - HEIGHT) && (position.y > p.y)) {
			return true;
		}
		return false;
	}

	@Override
	public PinView getInputPinView(Pin pin) {
		return null;
	}
	
	@Override
	public void setFantom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PinView getOutputPinView(Pin pin) {
		for(PinView pw: outputs) {
			if (pw.getPin() == pin) {
				return pw;
			}
		}
		return null;
	}
	
}