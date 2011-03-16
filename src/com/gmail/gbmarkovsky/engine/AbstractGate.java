package com.gmail.gbmarkovsky.engine;

/**
 * Абстрактная реализация логического элемента.
 * @author george
 *
 */
public abstract class AbstractGate implements Gate {
	private GateType type;

	public AbstractGate(GateType type) {
		this.type = type;
	}
	
	public GateType getType() {
		return type;
	}
}
