package com.gmail.gbmarkovsky.le.circuit;

public enum Signal {
	TRUE, FALSE, NONE;
	
	public static Signal not(Signal signal) {
		if (signal == TRUE) {
			return FALSE;
		} else if (signal == FALSE) {
			return TRUE;
		} else if (signal == NONE) {
			return NONE;
		}
		return null;
	}
}
