package io.github.hl7.bridge;

public interface MessagePreProcessor {

	String process(String rawMessage);
}
