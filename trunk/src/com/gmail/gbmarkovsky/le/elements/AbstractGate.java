package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;

/**
 * Абстрактная реализация логического элемента.
 * @author george
 *
 */
public abstract class AbstractGate implements Gate {
	protected GateType type;
	protected final ArrayList<Pin> inputs;
	protected final Pin output;

	public AbstractGate(GateType type) {
		this.type = type;
		inputs = new ArrayList<Pin>(type.getInputCount());
		for (int i = 0; i < type.getInputCount(); i++) {
			inputs.add(Pin.createInput(this));
		}
		output = Pin.createOutput(this);
	}
	
	public GateType getType() {
		return type;
	}

	public ArrayList<Pin> getInputs() {
		return inputs;
	}

	public Pin getInput(int index) {
		return inputs.get(index);
	}
	
	public Pin getOutput() {
		return output;
	}
	
	public int getInputIndex(Pin pin) {
		return inputs.indexOf(pin);
	}
}
