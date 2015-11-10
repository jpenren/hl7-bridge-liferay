package io.github.hl7.bridge.handler;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.Subscribe;
import io.github.hl7.bridge.exchange.MessageDeliveryImpl;
import io.github.hl7.bridge.exchange.MessageExchangeImpl;

public class MessageListenerNotifierCallable implements Callable<Void>{
	private final Logger log = LoggerFactory.getLogger(MessageListenerNotifierCallable.class);
	private MessageListener listener;
	private MessageExchangeImpl exchange;
	
	public MessageListenerNotifierCallable(MessageExchangeImpl exchange, MessageListener listener) {
		this.exchange = exchange;
		this.listener = listener;
	}
	
	@Override
	public Void call() throws Exception {
		try {
			MessageDeliveryImpl delivery = new MessageDeliveryImpl();
			delivery.setDeliveredAt(new Date());
			delivery.setReceiver(listener.getClass().getName());
			
			notify(listener, exchange.getMessage());
			
			exchange.add(delivery);
		} catch (Exception e) {
			log.error("Error on message notification:", e);
			exchange.add(e);
		}
		
		return null;
	}

	private void notify(MessageListener listener, Message message) {
		//Search for annotated methods with Subscribe annotation
		//Check if parameter type matches and if regex in Subscribe annotation matches
		
    	Method[] methods = listener.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if( method.isAnnotationPresent(Subscribe.class) ){
				//parameter fits?
				Class<?>[] params = method.getParameterTypes();
				if( params!=null && params.length>0 && params[0].isAssignableFrom(message.getClass()) ){
					boolean methodMatches = true;
					
					//Check if regex apply
					String[] subscriptions = method.getAnnotation(Subscribe.class).value();
					if( subscriptions!=null && subscriptions.length>0 ) {
						methodMatches = false;
						for (int i = 0; i < subscriptions.length && !methodMatches; i++) {
							String regex = subscriptions[i];
							if( message.getName().matches( regex ) ){
								methodMatches = true;
							}
						}
					}
					
					if( methodMatches ){
						//Send message to the listener
						try {
							method.invoke(listener, message);
						} catch (Exception e) {
							log.error("Error calling listener method:", e);
						} 
					}
				}
			}
		}
    }
	
}
