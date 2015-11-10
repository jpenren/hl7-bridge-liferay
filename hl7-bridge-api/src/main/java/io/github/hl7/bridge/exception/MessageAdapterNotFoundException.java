package io.github.hl7.bridge.exception;

public class MessageAdapterNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MessageAdapterNotFoundException(String message) {
		super(message);
	}

}
