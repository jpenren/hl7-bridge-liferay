package io.github.hl7.bridge;

import java.io.Serializable;
import java.util.Map;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.exception.HL7BridgeException;
import io.github.hl7.bridge.exception.MessageAdapterNotFoundException;

public interface HL7Bridge {
	
	Message publish(String rawMessage) throws MessageAdapterNotFoundException, HL7BridgeException;
	
	Message publish(String rawMessage, Map<String, Serializable> parameters) throws MessageAdapterNotFoundException, HL7BridgeException;
	
	Message publish(String rawMessage, MessageProcessDelegate delegate) throws MessageAdapterNotFoundException, HL7BridgeException;
	
	Message publish(String rawMessage, Map<String, Serializable> parameters, MessageProcessDelegate delegate) throws MessageAdapterNotFoundException, HL7BridgeException;
	
	void handle(Message message);
	
	void handle(Message message, MessageProcessDelegate delegate);
	
}
