package de.ifgi.streams.message;

import java.util.Map;

public interface MessageHandler<T> {

	public  T handleMessage(byte[] message,
			Map<String, Object> params);

	public  boolean isResponsibleForMessage(byte[] message,
			Map<String, Object> params);

}