package com.gmail.gbmarkovsky.le.circuit;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gmail.gbmarkovsky.le.elements.Connector;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.Wire;

/**
 * Схема как набор логических элементов, набор входов, набор выходов и набор проводников.
 * @author george
 *
 */
public class Circuit {
	public static final String WIRE_DELETE = "wireDelete";
	
	private Collection<Element> elements = new ArrayList<Element>();
	private List<Connector> inputs = new ArrayList<Connector>();
	private List<Connector> outputs = new ArrayList<Connector>();
	private Collection<Wire> wires = new ArrayList<Wire>();
	
	private PropertyChangeSupport propertyChangeSupport;
	
	public Circuit() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addElement(Element element) {
		elements.add(element);
	}
	
	public void addInput(Connector input) {
		inputs.add(input);
	}
	
	public void addOutput(Connector output) {
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
	public boolean deleteElement(Element element) {
		List<Wire> wiresToDisconnect = new ArrayList<Wire>();
		for (Pin pin: element.getOutputs()) {
			wiresToDisconnect.addAll(pin.getWires());
		}
		for (Pin pin: element.getInputs()) {
			wiresToDisconnect.addAll(pin.getWires());
		}
		for (Wire wire: wiresToDisconnect) {
			wires.remove(wire);
			wire.disconnect();
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		return elements.remove(element);
	}
	
	/**
	 * Удаляет вход из схемы.
	 * @param gate
	 * @return <code>true</code> если такой вход есть в схеме, иначе <code>false</code>
	 */
	public boolean deleteInput(Connector input) {
		List<Wire> wiresToDisconnect = new ArrayList<Wire>(input.getOutputs().get(0).getWires());
		for (Wire wire: wiresToDisconnect) {
			wires.remove(wire);
			wire.disconnect();
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		return inputs.remove(input);
	}
	
	/**
	 * Удаляет выход из схемы.
	 * @param gate
	 * @return <code>true</code> если такой выход есть в схеме, иначе <code>false</code>
	 */
	public boolean deleteOutput(Connector output) {
		List<Wire> wiresToDisconnect = new ArrayList<Wire>(output.getInputs().get(0).getWires());
		for (Wire wire: wiresToDisconnect) {
			wires.remove(wire);
			wire.disconnect();
			firePropertyChange(WIRE_DELETE, wire, null);
		}
		return outputs.remove(output);
	}
	
	public Collection<Element> getGates() {
		return elements;
	}

	public List<Connector> getInputs() {
		return inputs;
	}

	public List<Connector> getOutputs() {
		return outputs;
	}

	public Collection<Wire> getWires() {
		return wires;
	}
	

	public void deleteWire(Wire wire) {
		wires.remove(wire);
		wire.disconnect();
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
