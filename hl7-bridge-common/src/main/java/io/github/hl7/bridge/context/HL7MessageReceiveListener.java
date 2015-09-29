package io.github.hl7.bridge.context;

import io.github.hl7.bridge.HL7Bridge;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

final class HL7MessageReceiveListener implements MessageListener{
	private Logger log = LogManager.getLogger(HL7MessageReceiveListener.class);

	public void receive(final Message message) throws MessageListenerException {
		try {
			String payload = (String) message.getPayload();
			ca.uhn.hl7v2.model.Message hl7 = HL7Bridge.parse( payload );
			HL7Bridge.notify( hl7 );
		} catch (HL7Exception e) {
			log.error("Error processing message", e);
		}
	}

}
