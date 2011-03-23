package com.gmail.gbmarkovsky.le.engine;

/**
 * Проводник, соединяющий контакты.
 * @author george
 *
 */
public class Wire {
	private Pin start;
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
