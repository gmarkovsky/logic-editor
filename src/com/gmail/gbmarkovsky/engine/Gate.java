package com.gmail.gbmarkovsky.engine;

import java.util.ArrayList;

/**
 * Логический элемент схемы.
 * @author george
 *
 */
public interface Gate {

	public GateType getType();
	public Pin getOutput();
	public ArrayList<Pin> getInputs();
}
