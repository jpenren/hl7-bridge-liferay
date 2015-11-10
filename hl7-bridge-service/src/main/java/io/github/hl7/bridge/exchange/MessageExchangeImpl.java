package io.github.hl7.bridge.exchange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.MessageDelivery;
import io.github.hl7.bridge.MessageExchange;

public class MessageExchangeImpl implements MessageExchange{
	private static final long serialVersionUID = 1L;
	private String identifier;
	private Message message;
	private Collection<Throwable> exceptions = Collections.synchronizedCollection(new ArrayList<Throwable>());
	private Collection<MessageDelivery> deliveries = Collections.synchronizedCollection(new ArrayList<MessageDelivery>());
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public Collection<Throwable> getExceptions() {
		return exceptions;
	}
	
	public void add(Throwable error){
		exceptions.add(error);
	}

	@Override
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public boolean hasError() {
		return !exceptions.isEmpty();
	}

	@Override
	public Collection<MessageDelivery> getDeliveries() {
		return deliveries;
	}
	
	public void add(MessageDelivery delivery) {
		this.deliveries.add(delivery);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageExchangeImpl [identifier=").append(identifier).append(", message=").append(message)
				.append(", exceptions=").append(exceptions).append(", deliveries=").append(deliveries).append("]");
		return builder.toString();
	}

}
