package com.walter.mqtt.producer.configure;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
@EnableIntegration
public class MqttConfigure {
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
	public MessageConverter messageConverter() {
		return new DefaultPahoMessageConverter();
	}
}
