package com.walter.mqtt.producer.controller;

import com.walter.mqtt.producer.controller.vo.Product;
import com.walter.mqtt.producer.publisher.MqttPublisher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push")
@Tag(name = "메시지 발행")
public class ProducerController {
	private final MqttPublisher mqttPublisher;

	public ProducerController(MqttPublisher mqttPublisher) {
		this.mqttPublisher = mqttPublisher;
	}

	@Operation(summary = "Push Message API", description = "MQTT로 메시지를 Producing")
	@PostMapping("/message")
	public void publishing(@RequestBody Product product) throws Exception {
		mqttPublisher.publishing(product);
	}
}
