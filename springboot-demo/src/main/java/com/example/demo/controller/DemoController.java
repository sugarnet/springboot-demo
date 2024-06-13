package com.example.demo.controller;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

	private final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	private final Environment environment;

	public DemoController(Environment environment) {
		this.environment = environment;
	}

	@GetMapping("/")
	public ResponseEntity<?> index() {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("UUID generated: {}", uuid);
		return new ResponseEntity<>(uuid, HttpStatus.OK);
	}

	@GetMapping("/uuid")
	public ResponseEntity<?> getUuid() {
		UUID uuid = UUID.randomUUID();
		LOGGER.info("UUID generated: {}", uuid);
		return new ResponseEntity<>(uuid, HttpStatus.OK);
	}

	@GetMapping("/message")
	public ResponseEntity<?> message() {

		String message = environment.getProperty("MESSAGE");

		if (Objects.isNull(message) || message.isEmpty()) {
			return new ResponseEntity<>("Default Message for springboot-demo [update-message] branch!!", HttpStatus.OK);
		}

		LOGGER.info("Message: {}", message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
