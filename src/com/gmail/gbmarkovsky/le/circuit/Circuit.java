package com.gmail.gbmarkovsky.le.circuit;

import java.util.ArrayList;
import java.util.Collection;

import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Wire;

/**
 * Схема как набор логических элементов, набор входов и набор выходов.
 * @author george
 *
 */
public class Circuit {
	private ArrayList<Gate> gates = new ArrayList<Gate>();
	private ArrayList<Input> inputs = new ArrayList<Input>();
	private ArrayList<Output> outputs = new ArrayList<Output>();
	private ArrayList<Wire> wires = new ArrayList<Wire>();
	
	public void addGate(Gate gate) {
		gates.add(gate);
	}
	
	public void addInput(Input input) {
		inputs.add(input);
	}
	
	public void addOutput(Output output) {
		outputs.add(output);
	}
	
	public void addWire(Wire wire) {
		wires.add(wire);
	}
	
	/**
	 * Удаляет гейт из схемы.
	 * @param gate
	 * @return <code>true</code> если такой гейт есть в схеме, иначе <code>false</code>
	 */
	public boolean deleteGate(Gate gate) {
		return gates.remove(gate);
	}
	
	public Collection<Gate> getGates() {
		return gates;
	}
}
