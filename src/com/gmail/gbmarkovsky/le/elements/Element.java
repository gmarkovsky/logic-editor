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
	public GateType getType();
}
