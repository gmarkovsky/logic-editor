package com.gmail.gbmarkovsky.le.elements;

public class Output implements Element {
	private Pin inPin;

	public Output() {
		this.inPin = Pin.createInput();
	}
	
	public Pin getPin() {
		return inPin;
	}
}
