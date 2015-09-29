package io.github.hl7.bridge;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;

public final class HL7Bridge {
	private Logger log = LogManager.getLogger(HL7Bridge.class);
	public static final String ENDPOINT_RECEIVE = "hl7.bridge.receive";
	private final HL7BridgeRegistry registry;
	private final Parser parser;
	
	private HL7Bridge() {
		registry = new HL7BridgeRegistry();
		parser = new GenericParser();
		log.debug("Initializing bridge:" + hashCode());
	}

	public static void subscribe(HL7MessageListener listener){
		InstanceHolder.INSTANCE.registry.subscribe(listener);
	}
	
	public static void subscribe(HL7MessageListener listener, String ... messageTypes){
		InstanceHolder.INSTANCE.registry.subscribe(listener, messageTypes);
	}
	
	public static Message parse(String hl7Message) throws HL7Exception{
		return InstanceHolder.INSTANCE.parser.parse(hl7Message);
	}
	
	public static void notify(Message message) {
		final String messageName = message.getName();
		final Map<String, List<HL7MessageListener>> subscribers = InstanceHolder.INSTANCE.registry.getSubscribers();
		final Set<Entry<String, List<HL7MessageListener>>> entries = subscribers.entrySet();
		for (Entry<String, List<HL7MessageListener>> entry : entries) {
			if( messageName.matches(entry.getKey()) ){
				List<HL7MessageListener> listeners = entry.getValue();
				for (HL7MessageListener listener : listeners) {
					listener.receive( message );
				}
			}
		}
	}
	
	/**
	 * Lazy context initialization
	 * @author jpenren
	 */
	private static class InstanceHolder{
		private static final HL7Bridge INSTANCE = new HL7Bridge();
	}
	
}
