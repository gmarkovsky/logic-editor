package com.gmail.gbmarkovsky.le.elements;

/**
 * Проводник, соединяющий контакты.
 * @author george
 *
 */
public class Wire {
	/**
	 * Выходной контакт элемента к которому присоединено начало проводника.
	 * Этот контакт может быть только выходным контактом элемента.
	 */
	private Pin start;
	
	/**
	 * Входной контакт элемента к которому присоединен конец проводника.
	 * Этот контакт может быть только входным контактом элемента.
	 */
	private Pin end;
	
	public Wire(Pin start, Pin end) {
		if (start.getType() != PinType.OUTPUT || end.getType() != PinType.INPUT) {
			throw new RuntimeException("Wrong wire connection");
		}
		this.start = start;
		this.end = end;
	}

	public Pin getStart() {
		return start;
	}

	public void setStart(Pin start) {
		this.start = start;
	}

	public Pin getEnd() {
		return end;
	}

	public void setEnd(Pin end) {
		this.end = end;
	}
	
	public void disconnect() {
		start.removeWire(this);
		end.removeWire(this);
	}
}
