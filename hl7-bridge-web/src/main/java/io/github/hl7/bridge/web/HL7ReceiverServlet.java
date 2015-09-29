package io.github.hl7.bridge.web;

import io.github.hl7.bridge.HL7Bridge;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

import com.liferay.portal.kernel.messaging.MessageBusUtil;

public class HL7ReceiverServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String FALLBACK_CONTENT_TYPE = "text/plain";
	private final Logger log = LoggerFactory.getLogger(HL7ReceiverServlet.class);
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException("GET method not supported!");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String contentType = req.getContentType();
		final String encoding = getCharset(req);
		final String content = IOUtils.toString(req.getInputStream(), encoding);

		try {
			//Validate message structure
			Message message = HL7Bridge.parse(content);
			
			MessageBusUtil.sendMessage(HL7Bridge.ENDPOINT_RECEIVE, content);
			
			//Set content-type and charset
			final String ack = message.generateACK().toString();
			resp.setContentType(contentType);
			resp.setCharacterEncoding(encoding);
			writeResponse(resp, ack);
		} catch (HL7Exception e) {
			log.error("Unable to parse HL7 message", e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeResponse(resp, e.getMessage());
		}
	}
	
	private String getContentType(HttpServletRequest request){
		final String contentType = request.getContentType();
		if( contentType==null ){
			log.warn("Request message has no content-type");
			return FALLBACK_CONTENT_TYPE;
		}
		
		return contentType;
	}
	
	private String getCharset(HttpServletRequest request){
		final String contentType = getContentType(request);
		int colonIndex = contentType.indexOf(';');
		if (colonIndex != -1) {
			String charsetDef = contentType.substring(colonIndex + 1).trim();
			if (charsetDef.startsWith("charset=")) {
				return charsetDef.substring(8);
			}
		}
		
		return DEFAULT_CHARSET;
	}
	
	private void writeResponse(HttpServletResponse response, String content) throws IOException{
		response.getWriter().write(content);
		response.flushBuffer();
	}

}
