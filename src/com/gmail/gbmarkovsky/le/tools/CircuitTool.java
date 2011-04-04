package com.gmail.gbmarkovsky.le.tools;

import java.awt.Graphics;
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener;

/**
 * Интерфейс для всех инструментов для работы со схемой.
 * @author george
 *
 */
public interface CircuitTool extends MouseListener, MouseMotionListener {
	public void paint(Graphics g);
}
