/*
 * Copyright (c) 2011, Georgy Markovsky 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 *  * Neither the name of  nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
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
	
	public Collection<Element> getElements() {
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
