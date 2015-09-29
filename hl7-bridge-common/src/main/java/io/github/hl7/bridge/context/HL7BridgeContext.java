package io.github.hl7.bridge.context;

import io.github.hl7.bridge.HL7Bridge;
import io.github.hl7.bridge.exception.ContextAlreadyStartedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.messaging.config.PluginMessagingConfigurator;

final class HL7BridgeContext {
	private PluginMessagingConfigurator configurator;
	
	private HL7BridgeContext() {
		
	}

	public void start() throws ContextAlreadyStartedException {
		
		if( isStarted() ){
			throw new ContextAlreadyStartedException("HL7 Bridge context already started!");
		}
		
		//Listeners
		HL7MessageReceiveListener listener = new HL7MessageReceiveListener();
		
		//Destinations
		SerialDestination destination = new SerialDestination();
		destination.setName(HL7Bridge.ENDPOINT_RECEIVE);
		destination.afterPropertiesSet();
		
		Map<String, List<MessageListener>> listenersMap = new HashMap<String, List<MessageListener>>();
		List<MessageListener> listeners = new ArrayList<MessageListener>();
		listeners.add(listener);
		listenersMap.put(HL7Bridge.ENDPOINT_RECEIVE, listeners);
		List<Destination> destinations = new ArrayList<Destination>();
		destinations.add(destination);
		
		//Configurator
		configurator = new PluginMessagingConfigurator();
		configurator.setDestinations(destinations);
		configurator.setMessageListeners(listenersMap);
		configurator.afterPropertiesSet();
	}
	
	public boolean isStarted(){
		return configurator != null;
	}

	public void shutdown() {
		configurator.destroy();
	}
	
	public static HL7BridgeContext getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	/**
	 * Lazy context initialization
	 * @author jpenren
	 */
	private static class InstanceHolder{
		private static final HL7BridgeContext INSTANCE = new HL7BridgeContext();
	}

}
