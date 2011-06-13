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

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

public class Task {
	private Map<String, String> table = new HashMap<String, String>();
	
	private int inputs;
	private int outputs;
	
	public Task(InputStream input) {
		parse(input);
	}
	
	public Task(String fileName) {
		FileInputStream fw = null;
		try {
			fw = new FileInputStream(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = null;
		try {
			bytes = new byte[fw.available()];
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			fw.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
		parse(arrayInputStream);
	}
	
	public Map<String, String> getTable() {
		return table;
	}

	public int getInputs() {
		return inputs;
	}

	public int getOutputs() {
		return outputs;
	}

	private void parse(InputStream stream)  {
		DOMImplementationRegistry registry;
		try {
			registry = DOMImplementationRegistry.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(); // shouldn't happen;
		}

		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");

		LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

		LSInput in = impl.createLSInput();
		in.setByteStream(stream);		
		Document doc = null;
		
		doc = parser.parse(in);
		parser.getDomConfig();
		
		org.w3c.dom.Element root = doc.getDocumentElement();
		
		//Читаем строки таблицы 
		NodeList list = root.getElementsByTagName("row");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
			String vector = element.getAttribute("vector");
			String value = element.getAttribute("value");
			inputs = vector.length();
			outputs = value.length();
			table.put(vector, value);
		}
	}
}
