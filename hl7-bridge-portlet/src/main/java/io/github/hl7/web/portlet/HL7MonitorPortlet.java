package io.github.hl7.web.portlet;

import io.github.hl7.bridge.HL7Bridge;
import io.github.hl7.bridge.HL7MessageListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import ca.uhn.hl7v2.model.Message;

import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class HL7MonitorPortlet
 */
public class HL7MonitorPortlet extends MVCPortlet {
	private final Map<String, Integer> messageCounter = new HashMap<String, Integer>();
 
	@Override
	public void init() throws PortletException {
		super.init();
		
		HL7Bridge.subscribe(new HL7MessageListener() {
			public void receive(Message message) {
				String messageName = message.getName();
				Integer received = messageCounter.get(messageName);
				messageCounter.put(messageName, received==null ? 1 : ++received);
			}
		});
		
	}
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		renderRequest.setAttribute("counter", messageCounter);
		super.doView(renderRequest, renderResponse);
	}

}
