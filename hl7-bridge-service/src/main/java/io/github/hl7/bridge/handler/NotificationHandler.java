package io.github.hl7.bridge.handler;

import java.util.List;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.MessageProcessDelegate;

public interface NotificationHandler {

	void handle(Message message, List<MessageListener> listeners, MessageProcessDelegate delegate);
	
}
