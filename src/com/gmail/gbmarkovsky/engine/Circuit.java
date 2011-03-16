package com.gmail.gbmarkovsky.engine;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Схема как набор логических элементов.
 * @author george
 *
 */
public class Circuit {
	private ArrayList<Gate> gates = new ArrayList<Gate>(); 
	
	public void addGate(Gate gate) {
		gates.add(gate);
	}
	
	public Collection<Gate> getGates() {
		return gates;
	}
}
