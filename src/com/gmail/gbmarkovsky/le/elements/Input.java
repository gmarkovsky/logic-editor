package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

public class Input implements Element {
	private Pin outPin;

	public Input() {
		this.outPin = Pin.createOutput(this);
	}
	
	public Pin getPin() {
		return outPin;
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

	@Override
	public int getInputIndex(Pin pin) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Pin getInput(int index) {
		throw new RuntimeException("Can't get input pin for input");
	}

	@Override
	public Pin getOutput() {
		return outPin;
	}
}
