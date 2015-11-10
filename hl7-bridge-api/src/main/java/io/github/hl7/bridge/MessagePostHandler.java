package io.github.hl7.bridge;

public interface MessagePostHandler {
	
	void postHandle(MessageExchange message);
}
