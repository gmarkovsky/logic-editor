package com.gmail.gbmarkovsky.engine;

/**
 * Контакт гейта, входа или выхода
 * @author george
 *
 */
public class Pin {
	private PinType type;
	
	private Pin(PinType type) {
		this.type = type;
	}
	
	public static Pin createInput() {
		return new Pin(PinType.INPUT);
	}

	public static Pin createOutput() {
		return new Pin(PinType.OUTPUT);
	}
	
	public PinType getType() {
		return type;
	}
}
