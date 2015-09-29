package io.github.hl7.bridge.exception;

public final class ContextAlreadyStartedException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ContextAlreadyStartedException(String msg) {
		super(msg);
	}

}
