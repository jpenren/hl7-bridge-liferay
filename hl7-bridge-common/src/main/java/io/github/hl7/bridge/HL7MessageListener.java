package io.github.hl7.bridge;

import ca.uhn.hl7v2.model.Message;

public interface HL7MessageListener {
	
	void receive(Message message);

}
