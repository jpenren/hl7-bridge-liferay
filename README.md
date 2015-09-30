# HL7 Bridge Liferay

This is an example project about how to implement a mechanism in Liferay to integrate with applications able to talk in HL7. The HL7 Bridge provides a way to centralize the reception of any HL7 message and send it to all portlets subscribed to this type of message.

#Receiving messages
Deploying the hl7-bridge-web module on Liferay, a new endpoint will be added, now Liferay can receive HL7 messages on /delegate/hl7-bridge (POST method).

#Subscription to receive messages
In any portlet that need to receive HL7 messages follow this steps:
- Add the hl7-bridge-common module as dependency
- Configure the ContextInitializationListener in web.xml
```xml
<listener>
	<listener-class>io.github.hl7.bridge.context.ContextInitializationListener</listener-class>
</listener>
```
- Subscribe to the HL7 bridge to receive messages. In your Portlet:
```java
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
```
