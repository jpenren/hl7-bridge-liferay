# HL7 Bridge Liferay

This is an example project about how to implement a mechanism in Liferay to integrate with applications able to talk in HL7. The HL7 Bridge provides a way to centralize the reception of any HL7 message and send it to all portlets subscribed to this type of message.

#Receiving messages
Deploying the hl7-bridge-web module on Liferay, a new endpoint will be added, now Liferay can receive HL7 messages on /delegate/hl7-bridge (POST method).

Example message:
```
POST http://host:port/delegate/hl7-bridge
Content-Type: application/hl7-v2+er7; charset=utf-8
MSH|^~\&|||||20150930091151.621+0200||ADT^A01^ADT_A01|501|T|2.5
...
```

#Subscription to receive messages
In any portlet that need to receive HL7 messages follow this steps:
- Add the hl7-bridge-common module as dependency
```xml
<dependency>
	<groupId>io.github.hl7bridge</groupId>
	<artifactId>hl7-bridge-common</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```
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
			//This is an HL7 message object
			String messageName = message.getName();
		}
	});
}
```
