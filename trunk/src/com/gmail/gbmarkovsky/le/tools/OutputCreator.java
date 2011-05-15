package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.circuit.Circuit;
import com.gmail.gbmarkovsky.le.elements.Output;
import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.CircuitView;
import com.gmail.gbmarkovsky.le.views.OutputView;

public class OutputCreator extends AbstractCircuitTool {
	private OutputView outputView;
	private boolean drawFantom;
	private boolean canPlace = true;
	
	public OutputCreator(CircuitEditor circuitEditor) {
		super(circuitEditor);
		outputView = new OutputView(new Point(0, 0), new Output());
		outputView.setFantom();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (canPlace) {
			Circuit circuit = circuitEditor.getCircuit();
			CircuitView circuitView = circuitEditor.getCircuitView();
			Output output = new Output();
			Point toPoint = new Point(arg0.getX() - outputView.getWidth(), arg0.getY() - outputView.getHeight());
			OutputView outputView = new OutputView(toPoint, output);
			circuit.addOutput(output);
			circuitView.addOutputView(outputView);
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
		Point toPoint = new Point(arg0.getX() - outputView.getWidth(), arg0.getY() - outputView.getHeight());
		outputView.setPosition(toPoint);
		circuitEditor.repaint();
		if (arg0.getX() - outputView.getWidth() < 0 || arg0.getY() - outputView.getHeight() < 0) {
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
			outputView.paint(g);
		}
	}

}
