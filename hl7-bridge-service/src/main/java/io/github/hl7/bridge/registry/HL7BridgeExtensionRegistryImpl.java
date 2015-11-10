package io.github.hl7.bridge.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import io.github.hl7.bridge.MessageAdapter;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.MessagePostHandler;
import io.github.hl7.bridge.MessagePreHandler;
import io.github.hl7.bridge.MessagePreProcessor;

@Component(immediate=true, service=HL7BridgeExtensionRegistry.class)
public class HL7BridgeExtensionRegistryImpl implements HL7BridgeExtensionRegistry{
	private final Map<String, MessageAdapter> adapters = new ConcurrentHashMap<>();
	private final List<MessageListener> listeners = Collections.synchronizedList(new ArrayList<MessageListener>());
	private final List<MessagePreProcessor> preProccessors = Collections.synchronizedList(new ArrayList<MessagePreProcessor>());
	private final List<MessagePreHandler> preHandlers = Collections.synchronizedList(new ArrayList<MessagePreHandler>());
	private final List<MessagePostHandler> postHandlers = Collections.synchronizedList(new ArrayList<MessagePostHandler>());
	
	@Reference(cardinality= ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
	protected void setMessageListener(MessageListener listener){
		this.listeners.add(listener);
	}
	
	protected void unsetMessageListener(MessageListener listener){
		this.listeners.remove(listener);
	}
    
    @Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
    protected void setMessageAdapter(MessageAdapter messageAdapter){
    	this.adapters.put(messageAdapter.getIdentifier(), messageAdapter);
    }
    
    protected void unsetMessageAdapter(MessageAdapter messageAdapter){
    	this.adapters.remove(messageAdapter.getIdentifier());
    }
    
    @Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
    protected void setMessagePreProcessor(MessagePreProcessor processor){
    	this.preProccessors.add(processor);
    }
    
    protected void unsetMessagePreProcessor(MessagePreProcessor processor){
    	this.preProccessors.remove(processor);
    }
    
    @Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
    protected void setMessagePreHandler(MessagePreHandler handler){
    	this.preHandlers.add(handler);
    }
    
    protected void unsetMessagePreHandler(MessagePreHandler handler){
    	this.preHandlers.remove(handler);
    }
    
    @Reference(cardinality=ReferenceCardinality.MULTIPLE, policy=ReferencePolicy.DYNAMIC)
    protected void setMessagePostHandler(MessagePostHandler handler){
    	this.postHandlers.add(handler);
    }
    
    protected void unsetMessagePostHandler(MessagePostHandler handler){
    	this.postHandlers.remove(handler);
    }
    
	@Override
	public Map<String, MessageAdapter> getMessageAdapters() {
		return adapters;
	}

	@Override
	public List<MessageListener> getMessageListeners() {
		return listeners;
	}
	
	@Override
	public List<MessagePreProcessor> getMessagePreProcessors() {
		return preProccessors;
	}

	@Override
	public List<MessagePreHandler> getMessagePreHandlers() {
		return preHandlers;
	}

	@Override
	public List<MessagePostHandler> getMessagePostHandlers() {
		return postHandlers;
	}
}
