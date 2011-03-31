package com.gmail.gbmarkovsky.le.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Wire;

public class CircuitSerializer {
	
	
	
	public static ByteArrayOutputStream write(Circuit circuit) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLOutputFactory f = XMLOutputFactory.newInstance();
		XMLStreamWriter w = null;
		try {
			 w = f.createXMLStreamWriter(out);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		int currentId = 0;
		Map<Element, Integer> gateToIdMap = new HashMap<Element, Integer>();

		try {
			w.writeStartDocument("utf-8", "1.0");
			w.writeStartElement("circuit");
			w.writeStartElement("elements");
			for (Element element: circuit.getGates()) {
				gateToIdMap.put(element, currentId);
				w.writeStartElement("element");
				w.writeAttribute("id", Integer.toString(currentId));
				w.writeAttribute("type", element.getType().toString());
				w.writeEndElement();
				w.writeCharacters("\n");
				currentId++;
			}
			w.writeEndElement();

			w.writeStartElement("inputs");
			for (Input input: circuit.getInputs()) {
				gateToIdMap.put(input, currentId);
				w.writeStartElement("input");
				w.writeAttribute("id", Integer.toString(currentId));
				w.writeEndElement();
				w.writeCharacters("\n");
				currentId++;
			}
			w.writeEndElement();
			
			w.writeStartElement("outputs");
			for (Output output: circuit.getOutputs()) {
				gateToIdMap.put(output, currentId);
				w.writeStartElement("output");
				w.writeAttribute("id", Integer.toString(currentId));
				w.writeEndElement();
				w.writeCharacters("\n");
				currentId++;
			}
			w.writeEndElement();
			
			w.writeStartElement("wires");
			for (Wire wire: circuit.getWires()) {
				w.writeStartElement("wire");
				w.writeAttribute("start", gateToIdMap.get(wire.getStart().getElement()).toString());
				w.writeAttribute("end", gateToIdMap.get(wire.getEnd().getElement()).toString());
				w.writeEndElement();
				w.writeCharacters("\n");
			}
			w.writeEndElement();
			w.writeEndDocument();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public static Circuit parse(InputStream stream) {
		Circuit circuit = new Circuit();
		
		return circuit;
	}
}
