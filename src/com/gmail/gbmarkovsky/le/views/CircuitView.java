package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Element;
import com.gmail.gbmarkovsky.le.elements.Gate;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.elements.Wire;

/**
 * Отображает логическую схему.
 * @author george
 *
 */
public class CircuitView {
	private Circuit circuit;
	private HashMap<Gate, GateView> gateViews = new HashMap<Gate, GateView>();
	private HashMap<Input, InputView> inputViews = new HashMap<Input, InputView>();
	private HashMap<Output, OutputView> outputViews = new HashMap<Output, OutputView>();
	private HashMap<Wire, WireView> wireViews = new HashMap<Wire, WireView>();
	private HashMap<Element, ElementView> elementViews = new HashMap<Element, ElementView>();
	
	public CircuitView(Circuit circuit) {
		this.circuit = circuit;
	}

	public Circuit getCircuit() {
		return circuit;
	}
	
	public void paint(Graphics g) {
		for (GateView gv: gateViews.values()) {
			gv.paint(g);
		}
		for (InputView iv: inputViews.values()) {
			iv.paint(g);
		}
		for (OutputView ov: outputViews.values()) {
			ov.paint(g);
		}
		for (WireView wv: wireViews.values()) {
			wv.paint(g);
		}
		for (ElementView ew: elementViews.values()) {
			ew.paint(g);
		}
	}

	public void addGateView(GateView gateView) {
		gateViews.put(gateView.getGate(), gateView);
	}
	
	public void addInputView(InputView inputView) {
		inputViews.put(inputView.getInput(), inputView);
	}
	
	public void addOutputView(OutputView outputView) {
		outputViews.put(outputView.getOutput(), outputView);
	}
	
	public void addWireView(WireView wireView) {
		wireViews.put(wireView.getWire(), wireView);
	}
	
	public void addElementView(ElementView elementView) {
		elementViews.put(elementView.getElement(), elementView);
	}
	
	public ElementView getElementViewForLocation(Point location) {
		for (GateView gv: gateViews.values()) {
			if (gv.isPointInsideView(location)) {
				return gv;
			}
		}
		for (InputView iv: inputViews.values()) {
			if (iv.isPointInsideView(location)) {
				return iv;
			}
		}
		for (OutputView ov: outputViews.values()) {
			if (ov.isPointInsideView(location)) {
				return ov;
			}
		}
		return null;
	}
	
	public PinView getPinViewForLocation(Point location) {
		for (GateView gv: gateViews.values()) {
			PinView pv = gv.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		for (InputView iv: inputViews.values()) {
			PinView pv = iv.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		for (OutputView ov: outputViews.values()) {
			PinView pv = ov.getPinViewForLocation(location);
			if (pv != null) {
				return pv;
			}
		}
		return null;
	}
	
	public List<ElementView> getElements() {
		ArrayList<ElementView> list = new ArrayList<ElementView>();
		list.addAll(gateViews.values());
		list.addAll(inputViews.values());
		list.addAll(outputViews.values());
		return list;
	}
	
	public List<WireView> getWires() {
		return new ArrayList<WireView>(wireViews.values());
	}
}
