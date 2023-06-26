package com.example.app;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.example.app.*" })
@EntityScan("com.example.app.entity")
@EnableJpaRepositories
@OpenAPIDefinition
@SecurityScheme(name = "Authorization", scheme = "apiKey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class TrainApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainApplication.class, args);

	}
}


