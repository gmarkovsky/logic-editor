package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Контакт гейта, вход или выход.
 * Если контакт является входом, то к нему может быть подсоединен один проводник.
 * В случае если это выход, то к нему может быть присоединено много проводников.
 * @author george
 *
 */
public class Pin {
	/**
	 * Тип контакта <code>{INPUT, OUTPUT}</code>.
	 */
	private PinType type;
	
	/**
	 * Провода присоединенные к контакту.
	 */
	private List<Wire> wires = new ArrayList<Wire>();
	
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

	public List<Wire> getWires() {
		return wires;
	}

	public void addWire(Wire wire) throws Exception {
		if (!((wires.size() > 0) && type.equals(PinType.INPUT))) {
			wires.add(wire);
		}
	}
}
