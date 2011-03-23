package com.gmail.gbmarkovsky.le.engine;

public class Output {
	private Pin inPin;

	public Output() {
		this.inPin = Pin.createInput();
	}
	
	public Pin getPin() {
		return inPin;
	}
}
