package io.github.hl7.bridge.exchange;

import java.util.Date;

import io.github.hl7.bridge.MessageDelivery;

public class MessageDeliveryImpl implements MessageDelivery{
	private static final long serialVersionUID = 1L;
	private String receiver;
	private Date deliveredAt;
	
	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public String getReceiver() {
		return receiver;
	}

	@Override
	public Date deliveredAt() {
		return deliveredAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageDeliveryImpl [receiver=").append(receiver).append(", deliveredAt=").append(deliveredAt)
				.append("]");
		return builder.toString();
	}

}
