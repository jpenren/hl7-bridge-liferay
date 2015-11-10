package io.github.hl7.bridge;

import java.io.Serializable;
import java.util.Map;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.exception.MessageTransformationException;

public interface MessageAdapter {

	String getIdentifier();
	
	int getPriority();
	
	boolean isAssignableFrom(String rawMessage, Map<String, Serializable> parameters);
	
	Message transform(String rawMessage, Map<String, Serializable> parameters) throws MessageTransformationException;
	
}
