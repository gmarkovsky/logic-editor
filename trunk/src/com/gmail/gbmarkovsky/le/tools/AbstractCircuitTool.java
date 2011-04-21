package com.gmail.gbmarkovsky.le.tools;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;

public abstract class AbstractCircuitTool implements CircuitTool {
	protected CircuitEditor circuitEditor;

	public AbstractCircuitTool(CircuitEditor circuitEditor) {
		this.circuitEditor = circuitEditor;
	}
}
