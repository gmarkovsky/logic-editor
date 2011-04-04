package com.gmail.gbmarkovsky.le.elements;

/**
 * Проводник, соединяющий контакты.
 * @author george
 *
 */
public class Wire {
	/**
	 * Выходной контакт элемента к которому присоединено начало проводника.
	 */
	private Pin start;
	
	/**
	 * Входной контакт элемента к которому присоединен конец проводника.
	 */
	private Pin end;
	
	public Wire(Pin start, Pin end) {
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
}
