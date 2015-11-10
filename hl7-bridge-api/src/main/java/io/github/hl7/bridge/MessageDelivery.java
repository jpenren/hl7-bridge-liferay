package io.github.hl7.bridge;

import java.io.Serializable;
import java.util.Date;

public interface MessageDelivery extends Serializable{
	
	String getReceiver();
	Date deliveredAt();

}
