package io.github.hl7.bridge.connector;

import javax.servlet.ServletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import io.github.hl7.bridge.HL7Bridge;

@Component(immediate=true)
public class HttpConnectorActivator {
	private static final String ENDPOINT = "/hl7-bridge";
	private HL7Bridge bridge;
	
	@Reference
	protected void setHL7Bridge(HL7Bridge bridge){
		this.bridge = bridge;
	}
	
	@Reference
	protected void init(HttpService http) throws ServletException, NamespaceException{
		http.registerServlet(ENDPOINT, new HttpConnectorServlet(bridge), null, null);
	}

}
