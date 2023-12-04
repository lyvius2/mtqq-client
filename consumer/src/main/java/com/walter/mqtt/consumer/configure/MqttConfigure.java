package com.walter.mqtt.consumer.configure;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
@EnableIntegration
public class MqttConfigure {
	private static final String TOPIC_NAME = "SAMPLE_TOPIC";

	@Value("${spring.mqtt.username}")
	private String username;

	@Value("${spring.mqtt.password:}")
	private String password;

	@Value("${spring.mqtt.broker-url:}")
	private String brokerUrls;

	@Bean
	public DefaultMqttPahoClientFactory defaultMqttPahoClientFactory() {
		final MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setServerURIs(brokerUrls.split(","));

		final DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
		clientFactory.setConnectionOptions(options);
		return clientFactory;
	}

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer messageProducer(DefaultMqttPahoClientFactory defaultMqttPahoClientFactory) {
		final MqttPahoMessageDrivenChannelAdapter adapter =
				new MqttPahoMessageDrivenChannelAdapter(brokerUrls, MqttAsyncClient.generateClientId(), defaultMqttPahoClientFactory, TOPIC_NAME);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler inboundMessageHandler() {
		return message -> {
			final String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
			log.info("Topic : {}, Payload : {}", topic, message.getPayload());
		};
	}
}
