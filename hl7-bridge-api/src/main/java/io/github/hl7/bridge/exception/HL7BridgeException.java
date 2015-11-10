package io.github.hl7.bridge.exception;

public class HL7BridgeException extends Exception {
	private static final long serialVersionUID = 1L;

	public HL7BridgeException(String message) {
		super(message);
	}
	
	public HL7BridgeException(String message,Throwable cause) {
		super(message, cause);
	}

}
