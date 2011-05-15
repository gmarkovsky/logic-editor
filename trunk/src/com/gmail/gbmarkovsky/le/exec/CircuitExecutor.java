package com.gmail.gbmarkovsky.le.exec;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Output;

public class CircuitExecutor {
	private Task task;

	public CircuitExecutor(Task task) {
		this.task = task;
	}
	
	public boolean execute(Circuit circuit) {
		for(String inputs : task.getTable().keySet()) {
			char[] inp = inputs.toCharArray();
			int i = 0;
			for(Input input : circuit.getInputs()) {
				input.setSignal(convert(inp[i]));
				i++;
			}
			char[] sym = new char[circuit.getOutputs().size()];
			i = 0;
			for(Output output : circuit.getOutputs()) {
				if (output.getSignal() == Signal.TRUE) {
					sym[i] = '1';
				} else {
					sym[i] = '0';
				}
				i++;
			}
			String res = new String(sym);
			if (!res.equals(task.getTable().get(inputs))) {
				return false;
				//boolean r = false;
			}
		}
		return true;
	}
	
	private Signal convert(char c) {
		if (c == '0') {
			return Signal.FALSE;
		} else {
			return Signal.TRUE;
		}
	}
}
