package com.gmail.gbmarkovsky.le.elements;

import com.gmail.gbmarkovsky.le.circuit.Signal;


/**
 * Реализация логического элемента.
 * @author george
 *
 */
public class Gate extends AbstractElement {
	protected GateType type;

	public Gate(GateType type) {
		super(type.getInputCount(), type.getOutputCount());
		this.type = type;
		execute();
	}
	
	public GateType getType() {
		return type;
	}
	
	public void execute() {
		if (type == GateType.AND) {
			Signal result = Signal.TRUE;
			for(Pin pin : inputs) {
				if (pin.getSignal() == Signal.FALSE) {
					result = Signal.FALSE;
					break;
				}
			}
			outputs.get(0).setSignal(result);
		} else if (type == GateType.OR) {
			Signal result = Signal.FALSE;
			for(Pin pin : inputs) {
				if (pin.getSignal() == Signal.TRUE) {
					result = Signal.TRUE;
				}
			}
			outputs.get(0).setSignal(result);
		} else if (type == GateType.NOT) {
			outputs.get(0).setSignal(Signal.not(inputs.get(0).getSignal()));
		} else if (type == GateType.CONST) {
				outputs.get(0).setSignal(inputs.get(0).getSignal());
			}
	}
}
