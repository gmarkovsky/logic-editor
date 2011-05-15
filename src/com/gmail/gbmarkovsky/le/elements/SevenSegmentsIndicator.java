package com.gmail.gbmarkovsky.le.elements;


public class SevenSegmentsIndicator extends AbstractElement {

	public SevenSegmentsIndicator() {
		super(0, 7);
	}

	@Override
	public Object getType() {
		return new String("SevenSegment");
	}
}
