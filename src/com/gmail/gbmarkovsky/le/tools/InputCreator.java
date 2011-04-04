package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.InputView;

public class InputCreator implements CircuitTool {
	private CircuitEditor editor;
	private InputView inputView;
	private boolean drawFantom;
	
	public InputCreator(CircuitEditor editor) {
		this.editor = editor;
		inputView = new InputView(new Point(0, 0), new Input());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = editor.getCircuit();
		CircuitView circuitView = editor.getCircuitView();
		Input input = new Input();
		InputView inputView = new InputView(new Point(arg0.getX(), arg0.getY()), input);
		circuit.addInput(input);
		circuitView.addInputView(inputView);
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
		inputView.setPosition(arg0.getPoint());
		editor.repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (drawFantom) {
			inputView.paint(g);
		}
	}

}
