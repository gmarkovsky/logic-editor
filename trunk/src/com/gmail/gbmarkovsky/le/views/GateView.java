package com.gmail.gbmarkovsky.le.views;

import com.gmail.gbmarkovsky.le.elements.Gate;

/**
 * Интерфейс для всех классов, отображающих логические элементы схемы.
 * @author george
 *
 */
public interface GateView extends ElementView {
	public Gate getGate();
}
