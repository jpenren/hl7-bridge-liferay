package io.github.hl7.bridge.registry;

import java.util.List;
import java.util.Map;

import io.github.hl7.bridge.MessageAdapter;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.MessagePreProcessor;
import io.github.hl7.bridge.MessagePostHandler;
import io.github.hl7.bridge.MessagePreHandler;

public interface HL7BridgeExtensionRegistry {
	
	Map<String, MessageAdapter> getMessageAdapters();
	List<MessageListener> getMessageListeners();
	List<MessagePreProcessor> getMessagePreProcessors();
	List<MessagePreHandler> getMessagePreHandlers();
	List<MessagePostHandler> getMessagePostHandlers();

}
