package io.github.hl7.bridge.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.HL7Bridge;
import io.github.hl7.bridge.MessageAdapter;
import io.github.hl7.bridge.MessageExchange;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.MessagePostHandler;
import io.github.hl7.bridge.MessagePreHandler;
import io.github.hl7.bridge.MessagePreProcessor;
import io.github.hl7.bridge.MessageProcessDelegate;
import io.github.hl7.bridge.exception.HL7BridgeException;
import io.github.hl7.bridge.exception.MessageAdapterNotFoundException;
import io.github.hl7.bridge.handler.NotificationHandlerManager;
import io.github.hl7.bridge.registry.HL7BridgeExtensionRegistry;

@Component(immediate = true, service = HL7Bridge.class)
public class HL7BridgeImpl implements HL7Bridge {
	private final Logger log = LoggerFactory.getLogger(HL7BridgeImpl.class);
	private HL7BridgeExtensionRegistry registry;

	@Override
	public Message publish(String rawMessage) throws HL7BridgeException {
		return publish(rawMessage, null, null);
	}
	
	@Override
	public Message publish(String rawMessage, Map<String, Serializable> parameters) throws HL7BridgeException {
		return publish(rawMessage, parameters, null);
	}

	@Override
	public Message publish(String rawMessage, MessageProcessDelegate delegate) throws HL7BridgeException {
		return publish(rawMessage, null, delegate);
	}
	
	@Override
	public Message publish(String rawMessage, Map<String, Serializable> parameters, MessageProcessDelegate delegate) throws HL7BridgeException {
		log.trace("Received message: {}", rawMessage);
		
		//pre-processors
		String finalMessage = rawMessage;
		List<MessagePreProcessor> preProcessors = registry.getMessagePreProcessors();
		for (MessagePreProcessor preProcessor : preProcessors) {
			finalMessage = preProcessor.process(finalMessage);
		}
		
		//find message adapter
		List<MessageAdapter> adapters = getSortedAdapters();
		for (MessageAdapter messageAdapter : adapters) {
			if( messageAdapter.isAssignableFrom(finalMessage, parameters) ){
				Message message = messageAdapter.transform(finalMessage, parameters);
				log.debug("Message managed by {} adapter", messageAdapter.getIdentifier());
				handle(message, delegate);
				
				return message;
			}
		}
	
		log.error("Message adapter not found");
		throw new MessageAdapterNotFoundException("No available adapters for this message");
	}
	
	@Override
	public void handle(Message message) {
		handle(message, null);
	}

	@Override
    public void handle(final Message message, final MessageProcessDelegate delegate) {
		log.trace("Handle message: {}", message);
		
    	//pre-handle: execute all pre-handlers
    	List<MessagePreHandler> handlers = registry.getMessagePreHandlers();
    	for (MessagePreHandler handler : handlers) {
			handler.preHandle(message);
		}
		
    	//notify to the listeners
    	List<MessageListener> listeners = registry.getMessageListeners();
    	NotificationHandlerManager.getInstance().handle(message, listeners, new MessageProcessDelegate() {
			
			@Override
			public void completed(MessageExchange exchange) {
				//post-handle: execute all post-handlers
				List<MessagePostHandler> handlers = registry.getMessagePostHandlers();
		    	for (MessagePostHandler handler : handlers) {
					handler.postHandle(exchange);
				}
				
				//Send progress status to the original delegate
				if( delegate!=null ){
					delegate.completed(exchange);
				}
			}
		});
    }
    
    private List<MessageAdapter> getSortedAdapters(){
    	//TODO store to avoid sort on every call 
    	final List<MessageAdapter> adapters = new ArrayList<>();
    	adapters.addAll(registry.getMessageAdapters().values());
		Collections.sort(adapters, new Comparator<MessageAdapter>() {
			
			@Override
			public int compare(MessageAdapter o1, MessageAdapter o2) {
				if( o1.getPriority()== o2.getPriority()){
					return 0;
				}
				
				return o1.getPriority()<o2.getPriority() ? -1 : 1;
			}
		});
		
		return adapters;
    }
    
    @Reference(cardinality=ReferenceCardinality.MANDATORY)
    protected void setHL7BridgeExtensionRegistry(HL7BridgeExtensionRegistry registry){
    	this.registry = registry;
    }

}