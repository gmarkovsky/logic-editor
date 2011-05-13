package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;

public class Output implements Element {
	private Pin inPin;
	
	private Signal signal = Signal.FALSE;

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

	@Override
	public int getInputIndex(Pin pin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pin getInput(int index) {
		if (index == 0) {
			return inPin;
		}
		throw new RuntimeException("Output has only one input pin");
	}

	@Override
	public Pin getOutput() {
		throw new RuntimeException("Can't get output pin for output");
	}
	
	public Signal getSignal() {
		return signal;
	}

	public void execute() {
		signal = inPin.getSignal();
	}
}
