package io.github.hl7.bridge.web;

import org.osgi.service.component.annotations.Component;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.MessageListener;
import io.github.hl7.bridge.Subscribe;

@Component(immediate=true, service=MessageListener.class)
public class ADTHL7MessageListener implements MessageListener{
	
	@Subscribe({"ADT.*"})
	public void receiveADT(Message message){
		//Stores message event
		DashboardRepository.getInstance().add(message);
	}

}
