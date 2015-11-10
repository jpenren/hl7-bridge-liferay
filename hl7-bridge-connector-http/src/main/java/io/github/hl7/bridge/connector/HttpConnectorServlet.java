package io.github.hl7.bridge.connector;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.hl7v2.model.Message;
import io.github.hl7.bridge.HL7Bridge;
import io.github.hl7.bridge.MessageExchange;
import io.github.hl7.bridge.MessageProcessDelegate;

@SuppressWarnings("unchecked")
class HttpConnectorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(HttpConnectorServlet.class); 
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String FALLBACK_CONTENT_TYPE = "text/plain";
	private HL7Bridge bridge;
	
	public HttpConnectorServlet(HL7Bridge bridge) {
		this.bridge = bridge;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new ServletException("Method not supported!");
	}
	
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		final String contentType = req.getContentType();
		final String encoding = getCharset(req);
		final String body = IOUtils.toString(req.getInputStream(), encoding);
		final Map<String, Serializable> params = req.getParameterMap();
		
		resp.setContentType(contentType);
		resp.setCharacterEncoding(encoding);
		
		try {
			Message message = bridge.publish(body, params, new MessageProcessDelegate() {

				@Override
				public void completed(MessageExchange exchange) {
					// Message processed by all listeners
					if( exchange.hasError() ) {
						// HL7 Enhanced mode
					}
				}
			});
			
			//Writes ACK response
			writeResponse(resp, message.generateACK().toString());
		} catch (Exception e) {
			log.error("Error processing returned message", e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
