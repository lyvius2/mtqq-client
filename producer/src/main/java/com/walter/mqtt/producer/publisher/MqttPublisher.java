package com.walter.mqtt.producer.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {
	private static final String TOPIC_NAME = "SAMPLE_TOPIC";
	private final DefaultMqttPahoClientFactory defaultMqttPahoClientFactory;
	private final MessageConverter messageConverter;
	private final ObjectMapper objectMapper;

	public MqttPublisher(DefaultMqttPahoClientFactory defaultMqttPahoClientFactory, MessageConverter messageConverter, ObjectMapper objectMapper) {
		this.defaultMqttPahoClientFactory = defaultMqttPahoClientFactory;
		this.messageConverter = messageConverter;
		this.objectMapper = objectMapper;
	}

	public void publishing(Object object) throws JsonProcessingException {
		final String payload = objectMapper.writeValueAsString(object);
		Message<String> message = MessageBuilder.withPayload(payload)
												.setHeader(MqttHeaders.TOPIC, TOPIC_NAME)
												.build();

		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), defaultMqttPahoClientFactory);
		messageHandler.setDefaultTopic(TOPIC_NAME);
		messageHandler.setConverter(messageConverter);
		messageHandler.handleMessage(message);
	}
}
