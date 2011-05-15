package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

/**
 * Интерфейс для всех элементов схемы.
 * Элемент схемы имеет входные и выходные контакты.
 * Входные контакты одних элементов схемы могут
 * соединяться с выходными контактами других элементов.
 * @author george
 *
 */
public interface Element {
	public List<Pin> getInputs();
	public List<Pin> getOutputs();
	public int getInputIndex(Pin pin);
	public Pin getInput(int index);
	public int getOutputIndex(Pin pin);
	public Pin getOutput(int index);
	public Object getType();
	//public Pin getOutput();
	//public GateType getType();
}
