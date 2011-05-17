package com.gmail.gbmarkovsky.le.views;

import java.awt.Point;

import com.gmail.gbmarkovsky.le.elements.Gate;

public class ConstGateView extends AbstractGateView {

	public ConstGateView(Point position, Gate gate) {
		super(position, gate, 16, 16, false);
	}

}
