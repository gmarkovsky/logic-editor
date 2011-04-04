package com.gmail.gbmarkovsky.le.elements;

/**
 * Тип элемента схемы.
 * @author george
 *
 */
public enum GateType {
	AND("AND", "&", 2), OR("OR", "1", 2), NOT("NOT", "~", 1);
	
	private GateType(String name, String symbol, int inputCount) {
		this.name = name;
		this.symbol = symbol;
		this.inputCount = inputCount;
	}
	
	private String name;
	private String symbol;
	private int inputCount;
	private int outputCount;

	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public int getInputCount() {
		return inputCount;
	}

	public int getOutputCount() {
		return outputCount;
	}
	
	public static GateType parseGateType(String string) {
		if (string.equals("AND")) {
			return AND;
		} else if (string.equals("OR")) {
			return OR;
		} else if (string.equals("NOT")) {
			return NOT;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
