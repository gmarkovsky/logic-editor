package com.gmail.gbmarkovsky.le.engine;

/**
 * Контакт гейта, вход или выход.
 * @author george
 *
 */
public class Pin {
	/**
	 * Тип контакта <code>{INPUT, OUTPUT}</code>.
	 */
	private PinType type;
	
	/**
	 * Провод присоединенный к контакту. Может быть <code>null</code>.
	 */
	private Wire wire;
	
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

	public Wire getWire() {
		return wire;
	}

	public void setWire(Wire wire) {
		this.wire = wire;
	}
}
