package com.ibc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Bharath API", version = "2.0", description = "Car Rental Information"))
@SecurityScheme(name = "bharathapi", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SpringbootCarRentalSystemAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCarRentalSystemAppApplication.class, args);
	}

}
