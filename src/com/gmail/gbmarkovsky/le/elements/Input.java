package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;

public class Input implements Element {
	private Pin outPin;
	
	private Signal signal = Signal.FALSE;

	public Input() {
		this.outPin = Pin.createOutput(this);
	}
	
	public Pin getPin() {
		return outPin;
	}
	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
		outPin.setSignal(this.signal);
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
