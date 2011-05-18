package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Connector;
import com.gmail.gbmarkovsky.le.elements.Connector.ConnectorType;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Wire;

/**
 * Отображает логическую схему.
 * @author george
 *
 */
public class CircuitView implements PropertyChangeListener {
	private Circuit circuit;
	//private HashMap<Gate, GateView> gateViews = new HashMap<Gate, GateView>();
	private HashMap<Connector, ConnectorView> inputViews = new HashMap<Connector, ConnectorView>();
	private HashMap<Connector, ConnectorView> outputViews = new HashMap<Connector, ConnectorView>();
	private HashMap<Wire, WireView> wireViews = new HashMap<Wire, WireView>();
	private HashMap<Element, ElementView> elementViews = new HashMap<Element, ElementView>();
	
	public CircuitView(Circuit circuit) {
		this.circuit = circuit;
	}

	public Circuit getCircuit() {
		return circuit;
	}
	
	public void paint(Graphics g) {
		//for (GateView gv: gateViews.values()) {
		//	gv.paint(g);
		//}
		for (ConnectorView iv: inputViews.values()) {
			iv.paint(g);
		}
		for (ConnectorView ov: outputViews.values()) {
			ov.paint(g);
		}
		for (WireView wv: wireViews.values()) {
			wv.paint(g);
		}
		for (ElementView ew: elementViews.values()) {
			ew.paint(g);
		}
	}

	//public void addGateView(GateView gateView) {
	//	gateViews.put(gateView.getGate(), gateView);
	//	addElementView(gateView);
	//}
	
	public void addInputView(ConnectorView inputView) {
		inputViews.put((Connector) inputView.getElement(), inputView);
		addElementView(inputView);
	}
	
	public void addOutputView(ConnectorView outputView) {
		outputViews.put((Connector) outputView.getElement(), outputView);
		addElementView(outputView);
	}
	
	public void addWireView(WireView wireView) {
		wireViews.put(wireView.getWire(), wireView);
	}
	
	public void addElementView(ElementView elementView) {
		elementViews.put(elementView.getElement(), elementView);
	}
	
	public ElementView getElementViewForLocation(Point location) {
		//for (GateView gv: gateViews.values()) {
		//	if (gv.isPointInsideView(location)) {
		//		return gv;
		//	}
		//}
		for (ElementView gv: elementViews.values()) {
			if (gv.isPointInsideView(location)) {
				return gv;
			}
		}
		for (ConnectorView iv: inputViews.values()) {
			if (iv.isPointInsideView(location)) {
				return iv;
			}
		}
		for (ConnectorView ov: outputViews.values()) {
			if (ov.isPointInsideView(location)) {
				return ov;
			}
		}
		for (WireView wv : wireViews.values()) {
			FractureView fv = wv.getFractureForLocation(location);
			if (fv != null)
			return fv;
		}
		return null;
	}
	
	public FractureView getFractureViewForLocation(Point location) {
		for (WireView wv : wireViews.values()) {
			FractureView fv = wv.getFractureForLocation(location);
			if (fv != null)
			return fv;
		}
		return null;
	}
	
	public PinView getPinViewForLocation(Point location) {
		//for (GateView gv: gateViews.values()) {
		//	PinView pv = gv.getPinViewForLocation(location);
		//	if (pv != null) {
		//		return pv;
		//	}
		//}
		for (ElementView gv: elementViews.values()) {
			PinView pv = gv.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		for (ConnectorView iv: inputViews.values()) {
			PinView pv = iv.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		for (ConnectorView ov: outputViews.values()) {
			PinView pv = ov.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		return null;
	}
	
	public List<ElementView> getElementsInsideRect(Point p, int w, int h) {
		List<ElementView> list = new ArrayList<ElementView>();
		for (ElementView ew: elementViews.values()) {
			if (ew.isInsideRect(p, w, h)) {
				list.add(ew);
			}
		}
		for (WireView wv : wireViews.values()) {
			List<FractureView> localList = wv.getFracturesInsideRect(p, w, h);
			
			if (!localList.isEmpty()) {
				list.addAll(localList);
			}
		}
		return list;
	}
	
	public List<ElementView> getElements() {
		ArrayList<ElementView> list = new ArrayList<ElementView>();
		//list.addAll(gateViews.values());
		list.addAll(inputViews.values());
		list.addAll(outputViews.values());
		list.addAll(elementViews.values());
		return list;
	}
	
	public List<WireView> getWires() {
		return new ArrayList<WireView>(wireViews.values());
	}
	
	public ElementView getElementView(Element element) {
		return elementViews.get(element);
	}
	
	public void deleteElementView(ElementView elementView) {
		elementViews.remove(elementView.getElement());
		if (elementView instanceof ConnectorView) {
			ConnectorView connectorView = (ConnectorView) elementView;
			if (connectorView.getType().equals(ConnectorType.INPUT)) {
				inputViews.remove(elementView.getElement());
				circuit.deleteInput((Connector) elementView.getElement());
			} else if (connectorView.getType().equals(ConnectorType.OUTPUT)) {
				outputViews.remove(elementView.getElement());
				circuit.deleteOutput((Connector) elementView.getElement());
			}
		} else {
			//gateViews.remove(elementView.getElement());
			elementViews.remove(elementView.getElement());
			circuit.deleteElement(elementView.getElement());
		}
	}

	public void deleteWire(WireView wireView) {
		wireViews.remove(wireView.getWire());
		wireView.disconnect();
		circuit.deleteWire(wireView.getWire());
	}
	
	public WireView getWireView(Wire wire) {
		return wireViews.get(wire);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == Circuit.WIRE_DELETE) {
			wireViews.remove(evt.getOldValue());
		}
	}
}
