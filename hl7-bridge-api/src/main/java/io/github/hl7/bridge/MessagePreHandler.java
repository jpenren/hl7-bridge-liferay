package io.github.hl7.bridge;

import ca.uhn.hl7v2.model.Message;

public interface MessagePreHandler {
	
	void preHandle(Message message);

}
