package com.gmail.gbmarkovsky.le.elements;

import com.gmail.gbmarkovsky.le.circuit.Signal;

/**
 * Проводник, соединяющий контакты.
 * @author george
 *
 */
public class Wire {
	/**
	 * Выходной контакт элемента, к которому присоединено начало проводника.
	 * Этот контакт может быть только выходным контактом элемента.
	 */
	private Pin start;
	
	/**
	 * Входной контакт элемента, к которому присоединен конец проводника.
	 * Этот контакт может быть только входным контактом элемента.
	 */
	private Pin end;
	
	private Signal signal = Signal.FALSE;
	
	public Wire(Pin start, Pin end) {
		if (start.getType() != PinType.OUTPUT || end.getType() != PinType.INPUT) {
			throw new RuntimeException("wrong wire connection");
		}
		
		this.start = start;
		this.end = end;
		
		this.start.addWire(this);
		this.end.addWire(this);
		
		this.setSignal(this.start.getSignal());
		this.end.setSignal(this.start.getSignal());
	}

	public Pin getStart() {
		return start;
	}

	public Pin getEnd() {
		return end;
	}
	
	public void setStart(Pin start) {
		this.start.removeWire(this);
		this.start = start;
		this.start.addWire(this);
		this.setSignal(this.start.getSignal());
	}

	public void setEnd(Pin end) {
		this.end.removeWire(this);
		this.end = end;
		this.end.addWire(this);
	}

	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
		end.setSignal(this.signal);
	}

	/**
	 * Отсоединить провод от контактов, к которым он присоединен.
	 */
	public void disconnect() {
		start.removeWire(this);
		end.removeWire(this);
		end.setSignal(Signal.FALSE);
	}
}
