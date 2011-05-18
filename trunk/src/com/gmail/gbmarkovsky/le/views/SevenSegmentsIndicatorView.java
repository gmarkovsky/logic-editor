package com.gmail.gbmarkovsky.le.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator.Segment;

public class SevenSegmentsIndicatorView implements ElementView {
	private static final int WIDTH = 135;
	private static final int HEIGHT = 190;
	private static final int WIDTH_LED = 110;
	private static final int HEIGHT_LED = 180;
	private static final int X_OFFSET = 15;
	private static final int Y_OFFSET = 5;
	private static final int TOP_SEGMENT_INSET = 26;
	private static final int MEDIUM_SEGMENT_INSET = 20;
	private static final int BOTTOM_SEGMENT_INSET = 14;
	
	private Map<String, SegmentView> segments = new HashMap<String, SegmentView>();
	private ArrayList<PinView> inputs = new ArrayList<PinView>();
	
	private Point position;
	private SevenSegmentsIndicator indicator;
	
	private boolean selected;
	
	private int alpha = 255;
	
	public SevenSegmentsIndicatorView(Point position,
			SevenSegmentsIndicator indicator) {
		this.position = position;
		this.indicator = indicator;
		calculatePositions();
		List<Point> outPos = getInputPositions();
		for (int i = 0; i < 7; i++) {
			inputs.add(new PinView(outPos.get(i), this.indicator.getInput(i)));
		}
	}

	private List<Point> getInputPositions() {
		int inputCount = 7;
		ArrayList<Point> list = new ArrayList<Point>(inputCount);
		for (int i = 1; i <= inputCount; i++) {
			list.add(new Point(position.x, position.y + i * (HEIGHT + 10) / (inputCount + 1)));
		}
		return list;
	}
	
	private void calculatePositions() {
		Point tl = new Point(position.x + TOP_SEGMENT_INSET + X_OFFSET, position.y + 20 + Y_OFFSET);
		Point tr = new Point(position.x + WIDTH_LED - BOTTOM_SEGMENT_INSET + X_OFFSET, position.y + 20 + Y_OFFSET);
		Point ml = new Point(position.x + MEDIUM_SEGMENT_INSET + X_OFFSET, position.y + HEIGHT_LED/2 + Y_OFFSET);
		Point mr = new Point(position.x + WIDTH_LED - MEDIUM_SEGMENT_INSET + X_OFFSET, position.y + HEIGHT_LED/2 + Y_OFFSET);
		Point bl = new Point(position.x + BOTTOM_SEGMENT_INSET + X_OFFSET, position.y + HEIGHT_LED - 20 + Y_OFFSET);
		Point br = new Point(position.x + WIDTH_LED - TOP_SEGMENT_INSET + X_OFFSET, position.y + HEIGHT_LED - 20 + Y_OFFSET);
		List<Segment> list = indicator.getSegments();
		segments.put(list.get(0).getId(), new SegmentView(new Point(tr.x, tr.y + 5), new Point(mr.x, mr.y - 5), list.get(0)));
		segments.put(list.get(1).getId(), new SegmentView(new Point(tl.x + 5, tl.y), new Point(tr.x - 5, tr.y), list.get(1)));
		segments.put(list.get(2).getId(), new SegmentView(new Point(tl.x, tl.y + 5), new Point(ml.x, ml.y - 5), list.get(2)));
		segments.put(list.get(3).getId(), new SegmentView(new Point(ml.x + 5, ml.y), new Point(mr.x - 5, mr.y), list.get(3)));
		segments.put(list.get(4).getId(), new SegmentView(new Point(ml.x, ml.y + 5), new Point(bl.x, bl.y - 5), list.get(4)));
		segments.put(list.get(5).getId(), new SegmentView(new Point(bl.x + 5, bl.y), new Point(br.x - 5, br.y), list.get(5)));
		segments.put(list.get(6).getId(), new SegmentView(new Point(mr.x, mr.y + 5), new Point(br.x, br.y - 5), list.get(6)));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(new Color(255, 255, 255, alpha));

		g2.fillRect(position.x, position.y, WIDTH, HEIGHT);
		
		g2.setColor(new Color(0, 0, 0, alpha));
		Stroke tmpStroke = g2.getStroke();
		Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);
		g2.draw(new Rectangle2D.Double(position.x, position.y, WIDTH, HEIGHT));
		g2.setStroke(tmpStroke);

		for (SegmentView segment: segments.values()) {
			segment.paint(g);
		}
		for (PinView pinView: inputs) {
			pinView.paint(g);
		}
		
		if (selected) {
			g.setColor(Color.red);
			g.drawRect(position.x, position.y, WIDTH, HEIGHT);
		}
		
		drawWires(g);
	}

	private void drawWires(Graphics g) {
		g.setColor(new Color(128, 128, 128, alpha));
		
		Point p = getInputPositions().get(1);
		SegmentView s = segments.get("b");
		int[] xs = new int[] {p.x, cX(35), s.getCenter().x, s.getCenter().x};
		int[] ys = new int[] {p.y, cY(15), cY(15), s.getCenter().y};
		g.drawPolyline(xs, ys, 4);
		
		p = getInputPositions().get(0);
		s = segments.get("a");
		xs = new int[] {p.x, cX(20), cX(125), cX(125), s.getCenter().x};
		ys = new int[] {p.y, cY(8), cY(8), s.getCenter().y, s.getCenter().y};
		g.drawPolyline(xs, ys, 5);
		
		
		p = getInputPositions().get(2);
		s = segments.get("c");
		xs = new int[] {p.x, cX(20), s.getCenter().x};
		ys = new int[] {p.y, s.getCenter().y, s.getCenter().y};
		g.drawPolyline(xs, ys, 3);
		
		p = getInputPositions().get(3);
		s = segments.get("d");
		xs = new int[] {p.x, cX(20), s.getCenter().x};
		ys = new int[] {p.y, s.getCenter().y, s.getCenter().y};
		g.drawPolyline(xs, ys, 3);
		
		p = getInputPositions().get(4);
		s = segments.get("e");
		xs = new int[] {p.x, cX(20), s.getCenter().x};
		ys = new int[] {p.y, s.getCenter().y, s.getCenter().y};
		g.drawPolyline(xs, ys, 3);
		
		p = getInputPositions().get(5);
		s = segments.get("f");
		xs = new int[] {p.x, cX(35), s.getCenter().x, s.getCenter().x};
		ys = new int[] {p.y, s.getCenter().y+11, s.getCenter().y+11, s.getCenter().y};
		g.drawPolyline(xs, ys, 4);
		
		
		p = getInputPositions().get(6);
		s = segments.get("g");
		xs = new int[] {p.x, cX(20), cX(117), cX(117), s.getCenter().x};
		ys = new int[] {p.y, cY(183), cY(183), s.getCenter().y, s.getCenter().y};
		g.drawPolyline(xs, ys, 5);
	}
	
	private int cX(int x) {
		return position.x + x;
	}
	
	private int cY(int y) {
		return position.y + y;
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
		int dx = position.x - this.position.x;
		int dy = position.y - this.position.y;
		this.position = position;
		List<Point> inputPositions = getInputPositions();
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).setPosition(inputPositions.get(i));
		}
		for (SegmentView sw : segments.values()) {
			sw.move(dx, dy);
		}
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
		for (PinView pv: inputs) {
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
		for (PinView pv : inputs) {
			if (pv.getPin() == pin){
				return pv;
			}
		}
		return null;
	}
	
	@Override
	public void setFantom() {
		alpha = 128;
		for(PinView pw: inputs) {
			pw.setFantom();
		}
		for (SegmentView sw : segments.values()) {
			sw.setFantom();
		}
	}

	@Override
	public PinView getOutputPinView(Pin pin) {
		for(PinView pw: inputs) {
			if (pw.getPin() == pin) {
				return pw;
			}
		}
		return null;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

class SegmentView {
	private Segment segment;
	
	private Point[] points = new Point[6];
	
	private int alpha = 255;
	
	public SegmentView(Point p, Point q, Segment segment) {
		this.segment = segment;
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
	
	public void paint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(0, 0, 0, alpha));
		for (int i = 0; i < points.length - 1; i++) {
			g.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
		}
		g.drawLine(points[0].x, points[0].y, points[points.length-1].x, points[points.length-1].y);
		if (segment.getSignal() == Signal.TRUE){
			int[] x = new int[6];
			int[] y = new int[6];
			for (int i = 0; i < points.length; i++) {
				x[i] = points[i].x;
				y[i] = points[i].y;
			}
			g.setColor(new Color(147, 205, 90, alpha));
			g.fillPolygon(x, y, 6);
		}
	}
	
	public void move(int dx, int dy) {
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(points[i].x + dx, points[i].y + dy);
		}
	}
	
	public void setFantom() {
		alpha = 128;
	}
	
	public Point getCenter() {
		return new Point((points[0].x + points[3].x)/2,(points[0].y + points[3].y)/2);
	}
}

//class TestComponent extends JComponent {
//	private static final long serialVersionUID = 597844575914526776L;
//	private SegmentView segment = new SegmentView(new Point(250, 250), new Point(400, 250));
//	
//	public TestComponent() {
//		super();
//		setSize(500, 500);
//	}
//	
//	public void paint(Graphics g) {
//		segment.paint(g);
//		g.setColor(Color.red);
//		g.drawLine(250, 0, 250, 500);
//		g.drawLine(0, 0, 500, 500);
//		g.drawLine(0, 0, 500, 250);
//		g.drawOval(-200, -200, 400, 400);
//	}
//}
//
//class TestFrame extends JFrame {
//	private static final long serialVersionUID = -450421342829114022L;
//
//	public TestFrame() {
//		super();
//		add(new TestComponent());
//		setSize(500, 500);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
//		setVisible(true);
//	}
//}