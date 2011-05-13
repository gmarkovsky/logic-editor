package com.gmail.gbmarkovsky.le.elements;

import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;

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

	@Override
	public void execute() {
		if (type == GateType.AND) {
			Signal result = Signal.TRUE;
			for(Pin pin : inputs) {
				if (pin.getSignal() == Signal.FALSE) {
					result = Signal.FALSE;
					break;
				}
			}
			output.setSignal(result);
		} else if (type == GateType.OR) {
			Signal result = Signal.FALSE;
			for(Pin pin : inputs) {
				if (pin.getSignal() == Signal.TRUE) {
					result = Signal.TRUE;
				}
			}
			output.setSignal(result);
		} else if (type == GateType.NOT) {
			output.setSignal(Signal.not(inputs.get(0).getSignal()));
		}
	}
}
