package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Контакт элемента, вход или выход.
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
	
	/**
	 * Фабричный метод для создания входа схемы.
	 * @return новый экземпляр входа схемы
	 */
	public static Pin createInput() {
		return new Pin(PinType.INPUT);
	}

	/**
	 * Фабричный метод для создания выхода схемы.
	 * @return новый экземпляр выхода схемы
	 */
	public static Pin createOutput() {
		return new Pin(PinType.OUTPUT);
	}
	
	public PinType getType() {
		return type;
	}

	/**
	 * Возвращает список проводов, подключенных к контакту.
	 * @return список проводов, подключенных к контакту
	 */
	public List<Wire> getWires() {
		return wires;
	}

	/**
	 * Добавляет новый провод к контакту.
	 * Если контакт входной, то к нему можно подсоединить не более 1 провода.
	 * @param wire
	 */
	public void addWire(Wire wire) {
		if (!((wires.size() > 0) && type.equals(PinType.INPUT))) {
			wires.add(wire);
		}
	}
}
