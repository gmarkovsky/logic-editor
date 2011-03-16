package com.gmail.gbmarkovsky.views;

import java.awt.Graphics;
import java.util.HashMap;

import com.gmail.gbmarkovsky.engine.Circuit;
import com.gmail.gbmarkovsky.engine.Gate;

/**
 * Отображает логическую схему.
 * @author george
 *
 */
public class CircuitView {
	private Circuit circuit;
	private HashMap<Gate, GateView> gateViews = new HashMap<Gate, GateView>();
	
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
	}

	public void addGateView(GateView gateView) {
		gateViews.put(gateView.getGate(), gateView);
	}
}
