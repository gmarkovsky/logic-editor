package com.gmail.gbmarkovsky.le.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.gmail.gbmarkovsky.le.engine.Pin;
import com.gmail.gbmarkovsky.le.engine.PinType;

/**
 * Представление контакта.
 * @author george
 *
 */
public class PinView {
	public static final int PIN_WIDTH = 6;
	public static final int PIN_HEIGHT = 4;
	
	/**
	 * Точка крепления контакта к гейту.
	 */
	private Point position;
	
	/**
	 * Центр контакта.
	 */
	private Point center;
	
	private Pin pin;
	
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
		} else {
			center = new Point(position.x + PIN_WIDTH/2, position.y);
		}
	}

	/**
	 * Возвращает геометрический центр контакта.
	 * @return геометрический центр контакта
	 */
	public Point getCenter() {
		return center;
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
	
	/**
	 * Отрисовывает контакт на графическом контексте <code>g</code>.
	 * @param g графический контекст для отрисовки контакта
	 */
	public void paint(Graphics g) {
		//g.drawLine(position.x, position.y, point.x, point.y);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g.fillOval(point.x - 2, point.y - 2, PIN_DIAMETER, PIN_DIAMETER);
		g.fillRect(center.x - PIN_WIDTH/2, center.y - PIN_HEIGHT/2, PIN_WIDTH, PIN_HEIGHT);
	}
}
