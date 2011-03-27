package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;

/**
 * Логический элемент схемы.
 * @author george
 *
 */
public interface Gate extends Element {

	public GateType getType();
	public Pin getOutput();
	public ArrayList<Pin> getInputs();
}
