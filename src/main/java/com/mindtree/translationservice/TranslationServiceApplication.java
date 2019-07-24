package com.mindtree.translationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@SpringBootApplication
public class TranslationServiceApplication {
	@Value("${datosservidor.maximum}")
	private String db;
	
	public static void main(String[] args) {
		SpringApplication.run(TranslationServiceApplication.class, args);
	}
    @GetMapping("/hello")
	public String hello() {
		return "Hello"+db;
	}
}
