package io.github.hl7.bridge.exception;

public class MessageTransformationException extends HL7BridgeException {
	private static final long serialVersionUID = 1L;

	public MessageTransformationException(String message) {
		super(message);
	}
	
	public MessageTransformationException(String message, Throwable cause) {
		super(message, cause);
	}

}
