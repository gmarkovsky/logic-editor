package com.gmail.gbmarkovsky.le.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.gmail.gbmarkovsky.le.gui.CircuitEditor;
import com.gmail.gbmarkovsky.le.views.ElementView;
import com.gmail.gbmarkovsky.le.views.InputView;
import com.gmail.gbmarkovsky.le.views.OutputView;

public class ElementSelector implements CircuitTool {
	private CircuitEditor circuitEditor;
	
	public ElementSelector(CircuitEditor circuitEditor) {
		super();
		this.circuitEditor = circuitEditor;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		ElementView selectedElement = circuitEditor.getCircuitView().getElementViewForLocation(arg0.getPoint());
		circuitEditor.setSelectedElement(selectedElement);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	public void paint(Graphics g) {
		ElementView selectedElement = circuitEditor.getSelectedElement();
		if (selectedElement != null) {
			g.setColor(Color.red);
			if (selectedElement instanceof InputView || selectedElement instanceof OutputView) {
				g.drawOval(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
						selectedElement.getWidth() + 2, selectedElement.getHeight() + 2);
			} else {
				g.drawRect(selectedElement.getPosition().x - 1, selectedElement.getPosition().y - 1, 
						selectedElement.getWidth() + 2, selectedElement.getHeight() + 2);
			}
		}
	}
}
