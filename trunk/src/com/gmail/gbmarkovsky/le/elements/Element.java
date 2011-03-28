package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

/**
 * Интерфейс для всех элементов схемы.
 * @author george
 *
 */
public interface Element {
	public List<Pin> getInputs();
	public List<Pin> getOutputs();
	public GateType getType();
}
