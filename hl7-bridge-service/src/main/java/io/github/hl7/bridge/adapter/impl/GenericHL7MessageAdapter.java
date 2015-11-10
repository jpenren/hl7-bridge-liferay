package io.github.hl7.bridge.adapter.impl;

import java.io.Serializable;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import io.github.hl7.bridge.MessageAdapter;
import io.github.hl7.bridge.exception.MessageTransformationException;

@Component(immediate=true, service=MessageAdapter.class)
public class GenericHL7MessageAdapter implements MessageAdapter{
	private final Logger log = LoggerFactory.getLogger(GenericHL7MessageAdapter.class);
	private final Parser parser = new GenericParser();

	@Override
	public String getIdentifier() {
		return GenericHL7MessageAdapter.class.getName();
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	public boolean isAssignableFrom(String rawMessage, Map<String, Serializable> parameters) {
		return true;
	}

	@Override
	public Message transform(String rawMessage, Map<String, Serializable> parameters) throws MessageTransformationException {
		
		try {
			return parser.parse(rawMessage);
		} catch (HL7Exception e) {
			log.error("Error while transform message:", e);
			throw new MessageTransformationException("Unable to transform message");
		}
	}

}
