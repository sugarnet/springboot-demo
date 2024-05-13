package com.example.demo.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

	private final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

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
}
