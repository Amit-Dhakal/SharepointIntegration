package com.java.sharepointintegrationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Sharepoint API Integration", version = "1.0", description = "Sharepoint API"))
public class SharepointintegrationapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharepointintegrationapiApplication.class, args);
	}

}
