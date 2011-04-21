package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Контакт элемента, входной или выходной.
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
	
	/**
	 * Элемент схемы, которому принадлежит контакт.
	 */
	private Element element;
	
	/**
	 * Значение сигнала на контакте.
	 */
	private boolean signal;
	
	private Pin(PinType type, Element element) {
		this.type = type;
		this.element = element;
	}
	
	/**
	 * Фабричный метод для создания входного контакта.
	 * @return новый экземпляр входа схемы
	 */
	public static Pin createInput(Element element) {
		return new Pin(PinType.INPUT, element);
	}

	/**
	 * Фабричный метод для создания выходного контакта.
	 * @return новый экземпляр выхода схемы
	 */
	public static Pin createOutput(Element element) {
		return new Pin(PinType.OUTPUT, element);
	}
	
	/**
	 * Возвращает тип контакта <code>{INPUT, OUTPUT}</code>.
	 * @return тип контакта
	 */
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

	public boolean removeWire(Wire wire) {
		return wires.remove(wire);
	}
	
	public boolean isSignal() {
		return signal;
	}

	public void setSignal(boolean signal) {
		this.signal = signal;
	}

	public Element getElement() {
		return element;
	}
	
	public boolean isNewConnectAllowed() {
		if ((type == PinType.INPUT) && (wires.size() > 0)) {
			return false;
		}
		return true;
	}
}
