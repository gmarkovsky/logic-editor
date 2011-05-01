package com.gmail.gbmarkovsky.le.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.gmail.gbmarkovsky.le.elements.Pin;
import com.gmail.gbmarkovsky.le.elements.PinType;

/**
 * Представление контакта.
 * @author george
 *
 */
public class PinView {
	public static final int PIN_WIDTH = 8;
	public static final int PIN_HEIGHT = 8;
	
	/**
	 * Точка крепления контакта к гейту.
	 */
	private Point position;
	
	/**
	 * Центр контакта.
	 */
	private Point center;
	
	/**
	 * Точка для подключения проводов.
	 */
	private Point border;
	
	private Pin pin;
	
	private ArrayList<WireView> wires = new ArrayList<WireView>();
	
	/**
	 * Создает представление контакта по заданной точке крепления к гейту <code>position</code>
	 * на схеме и контакту <code>pin</code>.
	 * @param position точка крепления контакта к гейту
	 * @param pin контакт для отображения
	 */
	public PinView(Point position, Pin pin) {
		this.pin = pin;
		setPosition(position);
	}
	
	/**
	 * Возвращает точку прикрепления контакта к гейту.
	 * @return точка прикрепления контакта к гейту
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Устанавливает точку прикрепления контакта к гейту.
	 * @param position точка прикрепления контакта к гейту
	 */
	public void setPosition(Point position) {
		this.position = position;
		if (pin.getType().equals(PinType.INPUT)) {
			center = new Point(position.x - PIN_WIDTH/2, position.y);
			border = new Point(position.x - PIN_WIDTH, position.y);
		} else {
			center = new Point(position.x + PIN_WIDTH/2, position.y);
			border = new Point(position.x + PIN_WIDTH, position.y);
		}
	}

	/**
	 * Возвращает геометрический центр контакта.
	 * @return геометрический центр контакта
	 */
	public Point getCenter() {
		return center;
	}
	
	public Point getBorder() {
		return border;
	}

	public Pin getPin() {
		return pin;
	}
	
	/**
	 * Определяет находится ли точка внутри изображения контакта.
	 * @param p точка
	 * @return <code>true</code> если точка находится внутри изображения контакта, иначе <code>false</code>
	 */
	public boolean isPointOnPin(Point p) {
		if ((p.x >= center.x - PIN_WIDTH/2) && (p.x <= center.x + PIN_WIDTH/2) &&
				(p.y >= center.y - PIN_HEIGHT/2) && (p.y <= center.y + PIN_HEIGHT/2)) {
			return true;
		}
		return false;
	}
	
	public void addWireView(WireView wireView) {
		if (!((wires.size() > 0) && pin.getType().equals(PinType.INPUT))) {
			wires.add(wireView);
		}
	}
	
	public void removeWireView(WireView wireView) {
		wires.remove(wireView);
	}
	
	public List<WireView> getWires() {
		return wires;
	}
	
	public boolean isWireOnPin(WireView wireView) {
		return wires.contains(wireView);
	}
	
	/**
	 * Отрисовывает контакт на графическом контексте <code>g</code>.
	 * @param g графический контекст для отрисовки контакта
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.gray);
		g2.fill(new RoundRectangle2D.Double(center.x - PIN_WIDTH/2,
				center.y - PIN_HEIGHT/2,
				PIN_WIDTH, PIN_HEIGHT,
                2, 2));
		//g.fillRect(center.x - PIN_WIDTH/2, center.y - PIN_HEIGHT/2, PIN_WIDTH, PIN_HEIGHT);
	}
}
