package com.gmail.gbmarkovsky.le.elements;

import java.util.ArrayList;
import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Signal;

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
	 * Элемент схемы, которому принадлежит контакт.
	 */
	private Element element;
	
	/**
	 * Значение сигнала на контакте.
	 */
	private Signal signal = Signal.FALSE;
	
	/**
	 * Провода присоединенные к контакту.
	 */
	private List<Wire> wires = new ArrayList<Wire>();
	
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
	 * Добавляет новый провод к контакту.
	 * Если контакт входной, то к нему можно подсоединить не более 1 провода.
	 * @param wire
	 */
	public void addWire(Wire wire) {
		if (!((wires.size() > 0) && type.equals(PinType.INPUT))) {
			wires.add(wire);
		}
	}

	/**
	 * Отсоединяет провод <code>wire</code> от контакта.
	 * @param wire провод для отсоединения
	 * @return <code>true</code>, если провод <code>wire</code> был подсоединен к контакту,
	 * иначе <code>false</code>
	 */
	public boolean removeWire(Wire wire) {
		return wires.remove(wire);
	}

	/**
	 * Возвращает список проводов, подключенных к контакту.
	 * @return список проводов, подключенных к контакту
	 */
	public List<Wire> getWires() {
		return wires;
	}
	
	/**
	 * Проверяет, возможно ли подсоединить к контакту новый провод.
	 * @return <code>true</code>, если возможно подсоединить к контакту новый провод,
	 * иначе <code>false</code>
	 */
	public boolean isNewConnectAllowed() {
		if ((type == PinType.INPUT) && (wires.size() > 0)) {
			return false;
		}
		return true;
	}

	public Element getElement() {
		return element;
	}
	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		//if (this.signal == signal) {
			//return;
		//} else {
			this.signal = signal;
			if (type == PinType.OUTPUT) {
				for(Wire wire : wires) {
					wire.setSignal(this.signal);
				}
			}
			if (element instanceof Gate && type == PinType.INPUT) {
				((Gate) element).execute();
			}
			if (element instanceof Output) {
				((Output) element).execute();
			}
		//}
	}
}
