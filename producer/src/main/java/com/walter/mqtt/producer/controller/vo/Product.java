package com.walter.mqtt.producer.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	@Schema(description = "일련번호", example = "1")
	private long seq;

	@Schema(description = "콘솔", example = "Play Station 4")
	private String consoleName;

	@Schema(description = "제조사", example = "SONY")
	private String manufacturer;
}
