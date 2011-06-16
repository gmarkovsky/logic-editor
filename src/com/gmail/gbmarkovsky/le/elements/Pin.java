/*
 * Copyright (c) 2011, Georgy Markovsky 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 *  * Neither the name of  nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
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
		//Preconditions.checkNotNull(element);
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
			if (element instanceof Connector) {
				((Connector) element).execute();
			}
			if (element instanceof SevenSegmentsIndicator) {
				((SevenSegmentsIndicator) element).execute();
			}
		//}
	}
}
