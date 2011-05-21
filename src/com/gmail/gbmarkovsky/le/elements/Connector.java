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

public class Connector extends AbstractElement {
	private ConnectorType type;
	
	private Signal signal = Signal.FALSE;
	
	public enum ConnectorType {
		INPUT(0, 1), OUTPUT(1, 0);
		
		private int inputCount;
		private int outputCount;
		
		private ConnectorType(int inputCount, int outputCount) {
			this.inputCount = inputCount;
			this.outputCount = outputCount;
		}

		public int getInputCount() {
			return inputCount;
		}

		public int getOutputCount() {
			return outputCount;
		}

	}
	
	public Connector(ConnectorType connectorType) {
		super(connectorType.getInputCount(), connectorType.getOutputCount());
		type = connectorType;
	}
	
	public static Connector createInput() {
		return new Connector(ConnectorType.INPUT);
	}
	
	public static Connector createOutput() {
		return new Connector(ConnectorType.OUTPUT);
	}

	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
		if (type.equals(ConnectorType.INPUT)) {
			outputs.get(0).setSignal(this.signal);
		}
	}

	public ConnectorType getType() {
		return type;
	}
	
	public void execute() {
		if (type.equals(ConnectorType.OUTPUT)) {
			signal = inputs.get(0).getSignal();
		}
	}
}
