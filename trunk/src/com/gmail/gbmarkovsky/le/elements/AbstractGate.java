package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;

/**
 * Абстрактная реализация логического элемента.
 * @author george
 *
 */
public abstract class AbstractGate implements Gate {
	private GateType type;
	private final ArrayList<Pin> inputs;
	private final Pin output;

	public AbstractGate(GateType type) {
		this.type = type;
		inputs = new ArrayList<Pin>(type.getInputCount());
		for (int i = 0; i < type.getInputCount(); i++) {
			inputs.add(Pin.createInput());
		}
		output = Pin.createOutput();
	}
	
	public GateType getType() {
		return type;
	}

	public ArrayList<Pin> getInputs() {
		return inputs;
	}

	public Pin getOutput() {
		return output;
	}
}
