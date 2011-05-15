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
	private boolean canPlace = true;
	
	public InputCreator(CircuitEditor circuitEditor) {
		super(circuitEditor);
		inputView = new InputView(new Point(0, 0), new Input());
		inputView.setFantom();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (canPlace) {
			Circuit circuit = circuitEditor.getCircuit();
			CircuitView circuitView = circuitEditor.getCircuitView();
			Input input = new Input();
			Point toPoint = new Point(arg0.getX() - inputView.getWidth(), arg0.getY() - inputView.getHeight());
			InputView inputView = new InputView(toPoint, input);
			circuit.addInput(input);
			circuitView.addInputView(inputView);
			circuitEditor.updateSize();
			circuitEditor.repaint();
			canPlace = false;
		}
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
		Point toPoint = new Point(arg0.getX() - inputView.getWidth(), arg0.getY() - inputView.getHeight());
		inputView.setPosition(toPoint);
		circuitEditor.repaint();
		if (arg0.getX() - inputView.getWidth() < 0 || arg0.getY() - inputView.getHeight() < 0) {
			canPlace = false;
			drawFantom = false;
		} else {
			canPlace = true;
			drawFantom = true;
		}
	}

	@Override
	public void paint(Graphics g) {
		if (drawFantom) {
			inputView.paint(g);
		}
	}

}
