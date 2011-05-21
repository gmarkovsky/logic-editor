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

/**
 * Абстрактная реализация элемента схемы.
 * @author george
 *
 */
public abstract class AbstractElement implements Element {
	protected List<Pin> inputs;
	protected List<Pin> outputs;
	
	AbstractElement(int inputCount, int outputCount) {
		inputs = new ArrayList<Pin>(inputCount);
		for (int i = 0; i < inputCount; i++) {
			inputs.add(Pin.createInput(this));
		}
		outputs = new ArrayList<Pin>(outputCount);
		for (int i = 0; i < outputCount; i++) {
			outputs.add(Pin.createOutput(this));
		}
	}
	
	public List<Pin> getInputs() {
		return inputs;
	}
	
	public List<Pin> getOutputs() {
		return outputs;
	}
	
	public int getInputIndex(Pin pin) {
		return inputs.indexOf(pin);
	}
	
	public Pin getInput(int index) {
		return inputs.get(index);
	}
	
	public int getOutputIndex(Pin pin) {
		return outputs.indexOf(pin);
	}
	
	public Pin getOutput(int index) {
		return outputs.get(index);
	}
}
