package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.OutputView;

public class OutputCreator implements CircuitTool {
	private CircuitEditor editor;
	private OutputView outputView;
	private boolean drawFantom;
	
	public OutputCreator(CircuitEditor editor) {
		this.editor = editor;
		outputView = new OutputView(new Point(0, 0), new Output());
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = editor.getCircuit();
		CircuitView circuitView = editor.getCircuitView();
		Output output = new Output();
		OutputView outputView = new OutputView(new Point(arg0.getX(), arg0.getY()), output);
		circuit.addOutput(output);
		circuitView.addOutputView(outputView);
		editor.updateSize();
		editor.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawFantom = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		drawFantom = false;
		editor.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		outputView.setPosition(arg0.getPoint());
		editor.repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (drawFantom) {
			outputView.paint(g);
		}
	}

}
