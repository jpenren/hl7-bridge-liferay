package io.github.hl7.bridge;

import java.io.Serializable;
import java.util.Collection;

import ca.uhn.hl7v2.model.Message;

public interface MessageExchange extends Serializable{
	
	String getIdentifier();
	Message getMessage();
	boolean hasError();
	Collection<Throwable> getExceptions();
	Collection<MessageDelivery> getDeliveries();

}
