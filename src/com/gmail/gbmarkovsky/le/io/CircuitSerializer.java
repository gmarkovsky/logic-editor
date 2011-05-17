package com.gmail.gbmarkovsky.le.io;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Connector;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.GateType;
import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.SevenSegmentsIndicator;
import com.gmail.gbmarkovsky.le.elements.Wire;
import com.gmail.gbmarkovsky.le.views.AbstractGateView;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.ConnectorView;
import com.gmail.gbmarkovsky.le.views.ElementView;
import com.gmail.gbmarkovsky.le.views.SevenSegmentsIndicatorView;
import com.gmail.gbmarkovsky.le.views.WireView;

public class CircuitSerializer {
	
	public static ByteArrayOutputStream write(CircuitView circuitView) {
		DOMImplementationRegistry registry;
		try {
			registry = DOMImplementationRegistry.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(); // shouldn't happen;
		}

		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
		LSSerializer serializer = impl.createLSSerializer();

		DOMConfiguration config = serializer.getDomConfig();
		config.setParameter("format-pretty-print", Boolean.TRUE);

		LSOutput out = impl.createLSOutput();
		Writer ws = new StringWriter();
		out.setCharacterStream(ws);

		serializer.write(toDocument(circuitView), out);

		String res = ws.toString();

		try {
			ws.close();
		} catch (IOException e) {
			throw new RuntimeException(); // shouldn't happen;
		}

		byte[] resBytes = res.getBytes(Charset.forName("UTF-8"));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			byteArrayOutputStream.write(resBytes);
		} catch (IOException e) {
			throw new RuntimeException(); // shouldn't happen;
		}
		return byteArrayOutputStream;
	}
	

	private static Document toDocument(CircuitView circuitView) {
		Document doc;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(); // shouldn't happen;
		}

		Circuit circuit = circuitView.getCircuit();
		
		org.w3c.dom.Element root = doc.createElement("circuit");
		doc.appendChild(root);
		
		int currentId = 0;
		Map<Element, Integer> gateToIdMap = new HashMap<Element, Integer>();
		
		// Запись элементов схемы
		org.w3c.dom.Element elements = doc.createElement("elements");
		root.appendChild(elements);
		for (Element celement: circuit.getElements()) {
			gateToIdMap.put(celement, currentId);
			org.w3c.dom.Element element = doc.createElement("element");
			element.setAttribute("id", Integer.toString(currentId));
			element.setAttribute("type", celement.getType().toString());
			org.w3c.dom.Element view = doc.createElement("view");
			ElementView e = circuitView.getElementView(celement);
			view.setAttribute("x", Integer.toString(e.getPosition().x));
			view.setAttribute("y", Integer.toString(e.getPosition().y));
			element.appendChild(view);
			elements.appendChild(element);
			currentId++;
		}	
		
		//Запись входов
		org.w3c.dom.Element inputs = doc.createElement("inputs");
		root.appendChild(inputs);
		for (Connector cinput: circuit.getInputs()) {
			gateToIdMap.put(cinput, currentId);
			org.w3c.dom.Element input = doc.createElement("input");
			input.setAttribute("id", Integer.toString(currentId));
			org.w3c.dom.Element view = doc.createElement("view");
			ElementView e = circuitView.getElementView(cinput);
			view.setAttribute("x", Integer.toString(e.getPosition().x));
			view.setAttribute("y", Integer.toString(e.getPosition().y));
			input.appendChild(view);
			inputs.appendChild(input);
			currentId++;
		}
		
		//Запись выходов
		org.w3c.dom.Element outputs = doc.createElement("outputs");
		root.appendChild(outputs);
		for (Connector coutput: circuit.getOutputs()) {
			gateToIdMap.put(coutput, currentId);
			org.w3c.dom.Element output = doc.createElement("output");
			output.setAttribute("id", Integer.toString(currentId));
			org.w3c.dom.Element view = doc.createElement("view");
			ElementView e = circuitView.getElementView(coutput);
			view.setAttribute("x", Integer.toString(e.getPosition().x));
			view.setAttribute("y", Integer.toString(e.getPosition().y));
			output.appendChild(view);
			outputs.appendChild(output);
			currentId++;
		}

		//Запись проводов
		org.w3c.dom.Element wires = doc.createElement("wires");
		root.appendChild(wires);
		for (Wire cwire: circuit.getWires()) {
			org.w3c.dom.Element wire = doc.createElement("wire");
			wire.setAttribute("start", gateToIdMap.get(cwire.getStart().getElement()).toString());
			wire.setAttribute("end", gateToIdMap.get(cwire.getEnd().getElement()).toString());
			wire.setAttribute("inputIndex", Integer.toString(cwire.getEnd().getElement().getInputIndex(cwire.getEnd())));
			wire.setAttribute("outputIndex", Integer.toString(cwire.getStart().getElement().getOutputIndex(cwire.getStart())));
			wires.appendChild(wire);
		}
		return doc;
	}
	
	public static CircuitView parse(ByteArrayInputStream stream) throws CircuitLoadException {
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
		
		try {
			doc = parser.parse(in);
			parser.getDomConfig();
		} catch (LSException lsException) {
			throw new CircuitLoadException(lsException.code);
		}

		
		Circuit circuit = new Circuit();
		CircuitView circuitView = new CircuitView(circuit);
		org.w3c.dom.Element root = doc.getDocumentElement();

		Map<Integer, Element> idToElementMap = new HashMap<Integer, Element>();
		
		// Читаем гейты
		NodeList list = root.getElementsByTagName("element");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
			int id = Integer.parseInt(element.getAttribute("id"));
			String gateT = element.getAttribute("type");
			GateType gateType = GateType.parseGateType(gateT);
			NodeList ilist = element.getElementsByTagName("view");
			org.w3c.dom.Element view = (org.w3c.dom.Element) ilist.item(0);
			int x = Integer.parseInt(view.getAttribute("x"));
			int y = Integer.parseInt(view.getAttribute("y"));
			
			Element gate = null;
			ElementView gateView = null;
			if (gateType != null) {
				gate = new Gate(gateType);
				gateView = AbstractGateView.createFor(new Point(x, y), (Gate) gate);
			} else {
				if ("SevenSegment".equals(gateT)) {
					gate = new SevenSegmentsIndicator();
					gateView = new SevenSegmentsIndicatorView(new Point(x, y), (SevenSegmentsIndicator) gate);
				}
			}
			
			idToElementMap.put(id, gate);
			
			circuit.addElement(gate);
			circuitView.addElementView(gateView);
		}
		
		// Читаем входы
		list = root.getElementsByTagName("input");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
			int id = Integer.parseInt(element.getAttribute("id"));
			NodeList ilist = element.getElementsByTagName("view");
			org.w3c.dom.Element view = (org.w3c.dom.Element) ilist.item(0);
			int x = Integer.parseInt(view.getAttribute("x"));
			int y = Integer.parseInt(view.getAttribute("y"));
			
			Connector input = Connector.createInput();
			ConnectorView inputView = new ConnectorView(new Point(x, y), input);
			
			idToElementMap.put(id, input);
			
			circuit.addInput(input);
			circuitView.addInputView(inputView);
		}
		
		
		// Читаем выходы
		list = root.getElementsByTagName("output");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
			int id = Integer.parseInt(element.getAttribute("id"));
			NodeList ilist = element.getElementsByTagName("view");
			org.w3c.dom.Element view = (org.w3c.dom.Element) ilist.item(0);
			int x = Integer.parseInt(view.getAttribute("x"));
			int y = Integer.parseInt(view.getAttribute("y"));
			
			Connector output = Connector.createOutput();
			ConnectorView outputView = new ConnectorView(new Point(x, y), output);
			
			idToElementMap.put(id, output);
			
			circuit.addOutput(output);
			circuitView.addOutputView(outputView);
		}
		
		// Читаем провода
		list = root.getElementsByTagName("wire");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
			int start = Integer.parseInt(element.getAttribute("start"));
			int end = Integer.parseInt(element.getAttribute("end"));
			int inpIndex = Integer.parseInt(element.getAttribute("inputIndex"));
			int outIndex = Integer.parseInt(element.getAttribute("outputIndex"));
			
			Element startElement = idToElementMap.get(start);
			Element endElement = idToElementMap.get(end);
			
			Pin startPin = startElement.getOutput(outIndex);
			Pin endPin = endElement.getInput(inpIndex);
			
			ElementView startElementView = circuitView.getElementView(startElement);
			ElementView endElementView = circuitView.getElementView(endElement);
			
			Wire wire = new Wire(startPin, endPin);
			WireView wireView = new WireView(wire, startElementView.getOutputPinView(startPin), endElementView.getInputPinView(endPin));
			circuit.addWire(wire);
			circuitView.addWireView(wireView);
		}
		return circuitView;
	}
}