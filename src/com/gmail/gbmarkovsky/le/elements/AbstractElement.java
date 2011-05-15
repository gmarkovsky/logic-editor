package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактная реализация элемента схемы.
 * @author george
 *
 */
public abstract class AbstractElement implements Element {
	protected List<Pin> inputs;
	protected List<Pin> outputs;
	
	AbstractElement(int inputCount, int outputCount) {
		inputs = new ArrayList<Pin>(inputCount);
		for (int i = 0; i < inputCount; i++) {
			inputs.add(Pin.createInput(this));
		}
		outputs = new ArrayList<Pin>(outputCount);
		for (int i = 0; i < outputCount; i++) {
			outputs.add(Pin.createOutput(this));
		}
	}
	
	public List<Pin> getInputs() {
		return inputs;
	}
	
	public List<Pin> getOutputs() {
		return outputs;
	}
	
	public int getInputIndex(Pin pin) {
		return inputs.indexOf(pin);
	}
	
	public Pin getInput(int index) {
		return inputs.get(index);
	}
	
	public int getOutputIndex(Pin pin) {
		return outputs.indexOf(pin);
	}
	
	public Pin getOutput(int index) {
		return outputs.get(index);
	}
}
