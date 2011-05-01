package com.gmail.gbmarkovsky.le.io;

public class CircuitLoadException extends Exception {
	private static final long serialVersionUID = -930585916772932400L;
	
	private short code;

	public CircuitLoadException(short code) {
		super();
		this.code = code;
	}

	public short getCode() {
		return code;
	}
}
