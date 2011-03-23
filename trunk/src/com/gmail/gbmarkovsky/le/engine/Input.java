package com.gmail.gbmarkovsky.le.engine;

public class Input {
	private Pin outPin;

	public Input() {
		this.outPin = Pin.createOutput();
	}
	
	public Pin getPin() {
		return outPin;
	}
}
