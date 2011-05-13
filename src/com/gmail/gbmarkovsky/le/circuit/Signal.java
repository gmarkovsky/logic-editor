package com.gmail.gbmarkovsky.le.circuit;

public enum Signal {
	TRUE, FALSE;
	
	public static Signal not(Signal signal) {
		if (signal == TRUE) {
			return FALSE;
		} else if (signal == FALSE) {
			return TRUE;
		}
		return null;
	}
}
