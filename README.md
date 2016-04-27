# HL7 Bridge Liferay

This is an example project about how to implement a mechanism in Liferay to integrate with applications able to talk in HL7. The HL7 Bridge provides a way to centralize the reception of any HL7 message and send it to all portlets subscribed to this type of message.

![alt tag](https://raw.githubusercontent.com/jpenren/hl7-bridge-liferay/master/doc/images/HL7%20Bridge.png)

Architecture
![alt tag](https://raw.githubusercontent.com/jpenren/hl7-bridge-liferay/master/doc/images/HL7-Bridge-Architecture.png)

#Installation
- HL7 Bridge requires 'HAPI Java HL7 API OSGI Bundle', available from http://mvnrepository.com/artifact/ca.uhn.hapi/hapi-osgi-base/2.2, download it and copy to [liferay_home]/deploy
- Copy hl7-bridge modules (at least hl7-bridge-api, hl7-bridge-service and hl7-bridge-connector-http) to [liferay_home]/deploy

#Sending messages
With hl7-bridge-connector-http bundle active a new endpoint will be added and Liferay can receive HL7 messages at `/o/hl7-bridge` (POST method).

Example message:
```
POST http://host:port/o/hl7-bridge
Content-Type: application/hl7-v2+er7; charset=utf-8
MSH|^~\&|||||20150930091151.621+0200||ADT^A01^ADT_A01|501|T|2.5
...
```

#Subscription to receive messages
On any component that need to receive HL7 messages do the following steps:
- Add the hl7-bridge-api module as dependency
```xml
compile  'io.github.hl7-bridge:hl7-bridge-api:1.0.0-SNAPSHOT'
```

- Subscribe to the HL7 bridge to receive messages. In your component:
```java
import io.github.hl7.bridge.MessageListener;

@Component(immediate=true, service=MessageListener.class)
public class ADTHL7MessageListener implements MessageListener{
	
	@Subscribe({"ADT.*"})
	public void receiveADT(Message message){
		//Receive ADT messages
	}
	
	@Subscribe({"ADT_A01"})
	public void receiveADTA01(ADT_A01 message){
		//Receive ADT_A01 message
	}

}

```
