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



public class SevenSegmentsIndicator extends AbstractElement {

	private List<Segment> segments;
	
	public SevenSegmentsIndicator() {
		super(7, 0);
		segments = new ArrayList<Segment>();
		segments.add(new Segment("a"));
		segments.add(new Segment("b"));
		segments.add(new Segment("c"));
		segments.add(new Segment("d"));
		segments.add(new Segment("e"));
		segments.add(new Segment("f"));
		segments.add(new Segment("g"));
	}

	@Override
	public Object getType() {
		return new String("SevenSegment");
	}
	
	public void execute() {
		int i = 0;
		for(Pin pv : inputs) {
			segments.get(i).setSignal(pv.getSignal());
			i++;
		}
	}
	
	public List<Segment> getSegments() {
		return segments;
	}
	
	public class Segment {
		private String id;
		private Signal signal = Signal.FALSE;
		
		public Segment(String id) {
			this.id = id;
		}
		
		public String getId() {
			return id;
		}

		public Signal getSignal() {
			return signal;
		}

		public void setSignal(Signal signal) {
			this.signal = signal;
		}
	}
}


