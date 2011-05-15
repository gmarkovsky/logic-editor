package com.gmail.gbmarkovsky.le.elements;

import com.gmail.gbmarkovsky.le.circuit.Signal;

public class Connector extends AbstractElement {
	private ConnectorType type;
	
	private Signal signal = Signal.FALSE;
	
	public enum ConnectorType {
		INPUT(0, 1), OUTPUT(1, 0);
		
		private int inputCount;
		private int outputCount;
		
		private ConnectorType(int inputCount, int outputCount) {
			this.inputCount = inputCount;
			this.outputCount = outputCount;
		}

		public int getInputCount() {
			return inputCount;
		}

		public int getOutputCount() {
			return outputCount;
		}

	}
	
	public Connector(ConnectorType connectorType) {
		super(connectorType.getInputCount(), connectorType.getOutputCount());
		type = connectorType;
	}
	
	public static Connector createInput() {
		return new Connector(ConnectorType.INPUT);
	}
	
	public static Connector createOutput() {
		return new Connector(ConnectorType.OUTPUT);
	}

	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
		if (type.equals(ConnectorType.INPUT)) {
			outputs.get(0).setSignal(this.signal);
		}
	}

	public ConnectorType getType() {
		return type;
	}
	
	public void execute() {
		if (type.equals(ConnectorType.OUTPUT)) {
			signal = inputs.get(0).getSignal();
		}
	}
}
