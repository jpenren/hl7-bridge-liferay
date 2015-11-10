package io.github.hl7.bridge.web;

import java.util.HashMap;
import java.util.Map;

import ca.uhn.hl7v2.model.Message;

public class DashboardRepository {
	private static final DashboardRepository INSTANCE = new DashboardRepository();
	private final Map<String, Integer> messageCounter = new HashMap<String, Integer>();
	
	private DashboardRepository() {
		
	}
	
	public void add(Message message){
		String messageName = message.getName();
		Integer received = messageCounter.get(messageName);
		messageCounter.put(messageName, received==null ? 1 : ++received);
	}
	
	public Map<String, Integer> getMessageCounter(){
		return messageCounter;
	}
	
	public static DashboardRepository getInstance() {
		return INSTANCE;
	}

}
