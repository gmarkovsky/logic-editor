package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator;

public class SevenSegmentsIndicatorView implements ElementView {
	private static final int WIDTH = 110;
	private static final int HEIGHT = 180;
	private static final int TOP_SEGMENT_INSET = 26;
	private static final int MEDIUM_SEGMENT_INSET = 20;
	private static final int BOTTOM_SEGMENT_INSET = 14;
	
	private Collection<Segment> segments = new ArrayList<Segment>();
	private ArrayList<PinView> outputs = new ArrayList<PinView>();
	
	private Point position;
	private SevenSegmentsIndicator indicator;
	
	public SevenSegmentsIndicatorView(Point position,
			SevenSegmentsIndicator indicator) {
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
		Segment segment = new Segment(new Point(tl.x + 5, tl.y), new Point(tr.x - 5, tr.y));
		segment.setLight(true);
		segments.add(segment);
		segments.add(new Segment(new Point(ml.x + 5, ml.y), new Point(mr.x - 5, mr.y)));
		segments.add(new Segment(new Point(bl.x + 5, bl.y), new Point(br.x - 5, br.y)));
		
		segments.add(new Segment(new Point(tl.x, tl.y + 5), new Point(ml.x, ml.y - 5)));
		segments.add(new Segment(new Point(ml.x, ml.y + 5), new Point(bl.x, bl.y - 5)));
		
		segments.add(new Segment(new Point(tr.x, tr.y + 5), new Point(mr.x, mr.y - 5)));
		segments.add(new Segment(new Point(mr.x, mr.y + 5), new Point(br.x, br.y - 5)));
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(position.x, position.y, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.drawRect(position.x, position.y, WIDTH, HEIGHT);
		for (Segment segment: segments) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PinView getPinViewForLocation(Point location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPointInsideView(Point point) {
		// TODO Auto-generated method stub
		return false;
	}
	
//	public static void main(String[] args) {
//		new TestFrame();
//	}
}

class Segment {
	private Point[] points = new Point[6];
	private boolean light;
	
	public Segment(Point p, Point q) {
		int height = (int) getNorma(p, q);
		int width = height / 5;
		points[0] = new Point(0, 0);
		points[1] = new Point(height/6, width/2);
		points[2] = new Point(height - height/6, width/2);
		points[3] = new Point(height, 0);
		points[4] = new Point(height - height/6, -width/2);
		points[5] = new Point(height/6, -width/2);
		double angle = 0;
		int d = 0;
		if (getNorma(p) > getNorma(q)) {
			d = p.x - q.x;
		} else {
			d = q.x - p.x;
		}
		double arg = (double) d / (double) height;
		angle = Math.acos(arg);
		rotate(angle);
		moveToP(p, q);
	}

	private void moveToP(Point p, Point q) {
		int dx = 0;
		int dy = 0;
		if (getNorma(p) < getNorma(q)) {
			dx = p.x - points[0].x;
			dy = p.y - points[0].y;
		} else {
			dx = q.x - points[0].x;
			dy = q.y - points[0].y;
		}
		for (int i = 0; i < points.length; i++) {
			points[i].x = points[i].x + dx;
			points[i].y = points[i].y + dy;
		}
	}
	
	private void rotate(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		for (int i = 0; i < points.length; i++) {
			int x = points[i].x;
			int y = points[i].y;
			points[i].x = (int) (x * cos - y * sin);
			points[i].y = (int) (x * sin + y * cos);
		}
	}
	
	private double getNorma(Point p) {
		return Math.sqrt(p.x * p.x + p.y * p.y);
	}
	
	private double getNorma(Point p, Point q) {
		return Math.sqrt((p.x - q.x)*(p.x - q.x)+(p.y - q.y)*(p.y - q.y));
	}
	
	public void setLight(boolean light) {
		this.light = light;
	}

	public void paint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);
		for (int i = 0; i < points.length - 1; i++) {
			g.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
		}
		g.drawLine(points[0].x, points[0].y, points[points.length-1].x, points[points.length-1].y);
		if (light){
			int[] x = new int[6];
			int[] y = new int[6];
			for (int i = 0; i < points.length; i++) {
				x[i] = points[i].x;
				y[i] = points[i].y;
			}
			g.setColor(Color.green);
			g.fillPolygon(x, y, 6);
		}
	}
}

class TestComponent extends JComponent {
	private static final long serialVersionUID = 597844575914526776L;
	private Segment segment = new Segment(new Point(250, 250), new Point(400, 250));
	
	public TestComponent() {
		super();
		setSize(500, 500);
	}
	
	public void paint(Graphics g) {
		segment.paint(g);
		g.setColor(Color.red);
		g.drawLine(250, 0, 250, 500);
		g.drawLine(0, 0, 500, 500);
		g.drawLine(0, 0, 500, 250);
		g.drawOval(-200, -200, 400, 400);
	}
}

class TestFrame extends JFrame {
	private static final long serialVersionUID = -450421342829114022L;

	public TestFrame() {
		super();
		add(new TestComponent());
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}