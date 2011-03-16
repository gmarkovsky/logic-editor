package com.gmail.gbmarkovsky.engine;

/**
 * Тип элемента схемы.
 * @author george
 *
 */
public enum GateType {
	AND("AND", "&");
	
	private GateType(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}
	
	private String name;
	private String symbol;

	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
