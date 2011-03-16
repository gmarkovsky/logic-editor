package com.gmail.gbmarkovsky.engine;

/**
 * Класс реализации логического элемента конкретного типа.
 * @author george
 *
 */
public class LogicCell extends AbstractGate {

	private LogicCell(GateType type) {
		super(type);
	}

	public static Gate createAndGate() {
		return new LogicCell(GateType.AND);
	} 
}
