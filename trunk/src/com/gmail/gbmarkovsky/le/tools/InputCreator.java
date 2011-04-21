package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Input;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.InputView;

public class InputCreator extends AbstractCircuitTool {
	private InputView inputView;
	private boolean drawFantom;
	
	public InputCreator(CircuitEditor circuitEditor) {
		super(circuitEditor);
		inputView = new InputView(new Point(0, 0), new Input());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Circuit circuit = circuitEditor.getCircuit();
		CircuitView circuitView = circuitEditor.getCircuitView();
		Input input = new Input();
		InputView inputView = new InputView(new Point(arg0.getX(), arg0.getY()), input);
		circuit.addInput(input);
		circuitView.addInputView(inputView);
		circuitEditor.updateSize();
		circuitEditor.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawFantom = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		drawFantom = false;
		circuitEditor.repaint();
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
		circuitEditor.repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (drawFantom) {
			inputView.paint(g);
		}
	}

}
