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
