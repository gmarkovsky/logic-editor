package com.gmail.gbmarkovsky.le.engine;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Схема как набор логических элементов, набор входов и набор выходов.
 * @author george
 *
 */
public class Circuit {
	private ArrayList<Gate> gates = new ArrayList<Gate>();
	private ArrayList<Input> inputs = new ArrayList<Input>();
	private ArrayList<Output> outputs = new ArrayList<Output>();
	
	
	public void addGate(Gate gate) {
		gates.add(gate);
	}
	
	public void addInput(Input input) {
		inputs.add(input);
	}
	
	public void addOutput(Output output) {
		outputs.add(output);
	}
	
	public Collection<Gate> getGates() {
		return gates;
	}
}
