package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

	private final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	private Random random = new Random();
	private int colorIndex = random.nextInt(9 - 0) + 0;

	@GetMapping("/")
	public ResponseEntity<?> index() {

		List<String> colors = Arrays.asList("RED", "BLUE", "ORANGE", "PINK", "GRAY", "BLACK", "WHITE", "YELLOW",
				"GREEN");
		try {
			InetAddress id = InetAddress.getLocalHost();
			String color = colors.get(colorIndex);

			LOGGER.info("COLOR extracted: {} in {}", color, id.getHostName());
			return new ResponseEntity<>(color + " in " + id.getHostName(), HttpStatus.OK);
		} catch (UnknownHostException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/file")
	public ResponseEntity<?> readFile() {

		try {
			String filename = "/data/testFile.txt";

			Path path = Paths.get(filename);
			List<String> lines = Files.readAllLines(path);
			String text = String.join("\r\n", lines);

			LOGGER.info("Text file: {}", text);
			return new ResponseEntity<>(text, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error...", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
