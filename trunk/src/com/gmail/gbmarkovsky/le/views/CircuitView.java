package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import com.gmail.gbmarkovsky.le.engine.Circuit;
import com.gmail.gbmarkovsky.le.engine.Gate;
import com.gmail.gbmarkovsky.le.engine.Input;
import com.gmail.gbmarkovsky.le.engine.Output;

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
	
	public GateView getGateViewForLocation(Point location) {
		for (GateView gv: gateViews.values()) {
			if (gv.isPointInsideGateView(location)) {
				return gv;
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
		return null;
	}
}
