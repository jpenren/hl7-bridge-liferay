package io.github.hl7.bridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class HL7BridgeRegistry {
	
	public static final String ALL_MESSAGES = ".*";
	private final Map<String, List<HL7MessageListener>> subscribers = new HashMap<String, List<HL7MessageListener>>();
	
	public void subscribe(HL7MessageListener listener){
		subscribe(listener, ALL_MESSAGES);
	}
	
	public void subscribe(HL7MessageListener listener, String ... messageTypes){
		for (String key : messageTypes) {
			if( !subscribers.containsKey(key) ){
				subscribers.put(key, new ArrayList<HL7MessageListener>());
			}
			
			List<HL7MessageListener> subscribersPerType = subscribers.get(key);
			if( !subscribersPerType.contains(listener) ){
				subscribersPerType.add(listener);
			}
		}
	}

	public Map<String, List<HL7MessageListener>> getSubscribers(){
		return subscribers;
	}
	
	public List<HL7MessageListener> getSubscribers(String messageType){
		return subscribers.get(messageType);
	}
	
}
