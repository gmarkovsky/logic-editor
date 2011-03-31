package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

public class Output implements Element {
	private Pin inPin;

	public Output() {
		this.inPin = Pin.createInput(this);
	}
	
	public Pin getPin() {
		return inPin;
	}

	@Override
	public List<Pin> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pin> getOutputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GateType getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
