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
package com.gmail.gbmarkovsky.le.exec;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.circuit.Signal;
import com.gmail.gbmarkovsky.le.elements.Connector;

public class CircuitExecutor {
	private Task task;

	public CircuitExecutor(Task task) {
		this.task = task;
	}
	
	public boolean execute(Circuit circuit) {
		for(String inputs : task.getTable().keySet()) {
			char[] inp = inputs.toCharArray();
			int i = 0;
			for(Connector input : circuit.getInputs()) {
				input.setSignal(convert(inp[i]));
				i++;
			}
			char[] sym = new char[circuit.getOutputs().size()];
			i = 0;
			for(Connector output : circuit.getOutputs()) {
				if (output.getSignal() == Signal.TRUE) {
					sym[i] = '1';
				} else {
					sym[i] = '0';
				}
				i++;
			}
			String res = new String(sym);
			if (!res.equals(task.getTable().get(inputs))) {
				return false;
				//boolean r = false;
			}
		}
		return true;
	}
	
	private Signal convert(char c) {
		if (c == '0') {
			return Signal.FALSE;
		} else {
			return Signal.TRUE;
		}
	}
}
