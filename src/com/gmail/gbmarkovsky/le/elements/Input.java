package com.gmail.gbmarkovsky.le.elements;

public class Input implements Element {
	private Pin outPin;

	public Input() {
		this.outPin = Pin.createOutput();
	}
	
	public Pin getPin() {
		return outPin;
	}
}
