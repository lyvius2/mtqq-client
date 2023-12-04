package com.walter.mqtt.producer.publisher;

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

	public MqttPublisher(DefaultMqttPahoClientFactory defaultMqttPahoClientFactory, MessageConverter messageConverter) {
		this.defaultMqttPahoClientFactory = defaultMqttPahoClientFactory;
		this.messageConverter = messageConverter;
	}

	public void publishing(String payload) {
		Message<String> message = MessageBuilder.withPayload(payload)
												.setHeader(MqttHeaders.TOPIC, TOPIC_NAME)
												.build();

		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), defaultMqttPahoClientFactory);
		messageHandler.setDefaultTopic(TOPIC_NAME);
		messageHandler.setConverter(messageConverter);
		messageHandler.handleMessage(message);
	}
}
