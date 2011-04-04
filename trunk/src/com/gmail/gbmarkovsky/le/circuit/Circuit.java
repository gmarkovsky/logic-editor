package com.gmail.gbmarkovsky.le.circuit;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.Wire;

/**
 * Схема как набор логических элементов, набор входов, набор выходов и набор проводников.
 * @author george
 *
 */
public class Circuit {
	public static final String WIRE_DELETE = "wireDelete";
	
	private Collection<Gate> gates = new ArrayList<Gate>();
	private Collection<Input> inputs = new ArrayList<Input>();
	private Collection<Output> outputs = new ArrayList<Output>();
	private Collection<Wire> wires = new ArrayList<Wire>();
	
	private PropertyChangeSupport propertyChangeSupport;
	
	
	public Circuit() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
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
		for (Wire wire: gate.getOutput().getWires()) {
			wires.remove(wire);
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		for (Pin pin: gate.getInputs()) {
			for (Wire wire: pin.getWires()) {
				wires.remove(wire);
				firePropertyChange(WIRE_DELETE, wire, null);
			}
		}
		return gates.remove(gate);
	}
	
	/**
	 * Удаляет вход из схемы.
	 * @param gate
	 * @return <code>true</code> если такой вход есть в схеме, иначе <code>false</code>
	 */
	public boolean deleteInput(Input input) {
		for (Wire wire: input.getPin().getWires()) {
			wires.remove(wire);
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		return inputs.remove(input);
	}
	
	/**
	 * Удаляет выход из схемы.
	 * @param gate
	 * @return <code>true</code> если такой выход есть в схеме, иначе <code>false</code>
	 */
	public boolean deleteOutput(Output output) {
		for (Wire wire: output.getPin().getWires()) {
			wires.remove(wire);
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		return outputs.remove(output);
	}
	
	public Collection<Gate> getGates() {
		return gates;
	}

	public Collection<Input> getInputs() {
		return inputs;
	}

	public Collection<Output> getOutputs() {
		return outputs;
	}

	public Collection<Wire> getWires() {
		return wires;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.removePropertyChangeListener(p);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	} 
}
