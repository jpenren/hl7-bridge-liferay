package io.github.hl7.bridge.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.MessageProcessDelegate;
import io.github.hl7.bridge.exchange.MessageExchangeImpl;

public class NotificationHandlerManager implements NotificationHandler{
	private static final NotificationHandler INSTANCE = new NotificationHandlerManager();
	private final ExecutorService executor = Executors.newFixedThreadPool(20);
	private final Logger log = LoggerFactory.getLogger(NotificationHandlerManager.class);
	
	private NotificationHandlerManager() {
		
	}

	@Override
	public void handle(Message message, List<MessageListener> listeners, MessageProcessDelegate delegate) {
		MessageExchangeImpl exchange = new MessageExchangeImpl();
		exchange.setIdentifier(UUID.randomUUID().toString());
		exchange.setMessage(message);

		sendAndAwait(exchange, listeners, delegate);
	}
	
	private void sendAndAwait(final MessageExchangeImpl exchange, final List<MessageListener> listeners, final MessageProcessDelegate delegate){
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				//Creates tasks to execute on background
				List<MessageListenerNotifierCallable> tasks = new ArrayList<>();
				for (MessageListener listener : listeners) {
					tasks.add( new MessageListenerNotifierCallable(exchange, listener) );
				}
				
				try {
					executor.invokeAll(tasks);
					delegate.completed(exchange);
				} catch (InterruptedException e) {
					log.error("Unable to dispatch message:", e);
				}
			}
		});
		
	}
	
	public static NotificationHandler getInstance(){
		return INSTANCE;
	}

}
