package com.java.sharepointintegrationapi;

import com.java.sharepointintegrationapi.Config.SharepointConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//@EnableConfigurationProperties(SharepointConfiguration.class)
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Sharepoint API Integration", version = "1.0", description = "Sharepoint API"))
public class SharepointintegrationapiApplication {

//	@Bean
//	public static SharepointConfiguration getSharepointBean(){
//		return getSharepointBean();
//	}

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SharepointintegrationapiApplication.class, args);
		SharepointConfiguration config=context.getBean(SharepointConfiguration.class);
		config.show();
	}

}
