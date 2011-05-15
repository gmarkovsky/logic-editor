package com.gmail.gbmarkovsky.le.exec;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

	private void parse(ByteArrayInputStream stream)  {
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
