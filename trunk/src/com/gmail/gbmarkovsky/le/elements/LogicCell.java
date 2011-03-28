package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

/**
 * Класс реализации логического элемента конкретного типа.
 * @author george
 *
 */
public class LogicCell extends AbstractGate {

	public LogicCell(GateType type) {
		super(type);
	}
	
	public static Gate createAndGate() {
		return new LogicCell(GateType.AND);
	}

	@Override
	public List<Pin> getOutputs() {
		// TODO Auto-generated method stub
		return null;
	} 
}
