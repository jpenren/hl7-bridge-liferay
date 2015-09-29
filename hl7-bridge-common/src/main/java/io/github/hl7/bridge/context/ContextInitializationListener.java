package io.github.hl7.bridge.context;

import io.github.hl7.bridge.exception.ContextAlreadyStartedException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class ContextInitializationListener
 *
 */
public final class ContextInitializationListener implements ServletContextListener {
	private Logger log = LogManager.getLogger(ContextInitializationListener.class);

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         try {
			HL7BridgeContext.getInstance().start();
		} catch (ContextAlreadyStartedException e) {
			log.error("Error starting HL7 bridge context", e);
		}
    }
    
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	HL7BridgeContext.getInstance().shutdown();
    }
	
}
