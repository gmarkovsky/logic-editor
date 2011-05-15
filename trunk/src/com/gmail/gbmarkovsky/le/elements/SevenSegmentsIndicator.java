package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;



public class SevenSegmentsIndicator extends AbstractElement {

	private List<Segment> segments;
	
	public SevenSegmentsIndicator() {
		super(7, 0);
		segments = new ArrayList<Segment>();
		segments.add(new Segment("a"));
		segments.add(new Segment("b"));
		segments.add(new Segment("c"));
		segments.add(new Segment("d"));
		segments.add(new Segment("e"));
		segments.add(new Segment("f"));
		segments.add(new Segment("g"));
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
	
	public class Segment {
		private String id;
		private Signal signal = Signal.FALSE;
		
		public Segment(String id) {
			this.id = id;
		}
		
		public String getId() {
			return id;
		}

		public Signal getSignal() {
			return signal;
		}

		public void setSignal(Signal signal) {
			this.signal = signal;
		}
	}
}


