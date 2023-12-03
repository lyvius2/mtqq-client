package com.walter.mqtt.producer.configure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfigure {
	@Bean
	public OpenAPI openAPI() {
		final Info info = new Info().title("MQTT Producer API")
									.version("0.1");
		final Server server = new Server().url("/");
		final SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic");
		return new OpenAPI().info(info)
							.addServersItem(server)
							.components(new Components().addSecuritySchemes("basicScheme", securityScheme));
	}
}
