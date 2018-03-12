package com.spring.boot.apidoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@MapperScan("com.spring.boot.apidoc.mapper")
@ComponentScan(basePackages = {"com.spring.boot.apidoc"})
public class ApiDocApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDocApplication.class, args);
	}
}
