package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator.Segment;


public class SevenSegmentsDetector extends AbstractElement {

	private List<Segment> segments;
	
	public SevenSegmentsDetector() {
		super(0, 7);
		segments = new ArrayList<Segment>();
//		segments.add(new Segment("a"));
//		segments.add(new Segment("b"));
//		segments.add(new Segment("c"));
//		segments.add(new Segment("d"));
//		segments.add(new Segment("e"));
//		segments.add(new Segment("f"));
//		segments.add(new Segment("g"));
	}

	@Override
	public Object getType() {
		return new String("SevenSegment");
	}
	
	public void execute() {
		int i = 0;
		for(Pin pv : inputs) {
			segments.get(i).setSignal(pv.getSignal());
			i++;
		}
	}
	
	public List<Segment> getSegments() {
		return segments;
	}
}
