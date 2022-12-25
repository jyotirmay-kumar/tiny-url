package io.jyotirmay.tiny.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jyotirmay.tiny.dto.TinyUrlResponse;
import io.jyotirmay.tiny.dto.TinyUserRequest;
import io.jyotirmay.tiny.dto.TinyUserResponse;
import io.jyotirmay.tiny.service.AuthenticationService;
import io.jyotirmay.tiny.service.TinyUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Tag(name = "Tiny User v1.0")
public class TinyUserController {

	@Autowired
	private TinyUserService tinyUserService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = "/v1/tiny/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TinyUserResponse> createUser(@RequestBody TinyUserRequest tinyUserRequest) {
		log.info("Request received to register user {}", tinyUserRequest);

		TinyUserResponse tinyUserResponse = tinyUserService.createUser(tinyUserRequest);
		log.info("The response prepared for the request: {}", tinyUserResponse);

		return new ResponseEntity<>(tinyUserResponse, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/v1/tiny/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TinyUserResponse> deleteUser(@RequestHeader("user-id") int userId, @RequestHeader("api-key") String apiKey, @RequestBody TinyUserRequest tinyUserRequest) {
		log.info("Request received to delete user {}", tinyUserRequest);

		authenticationService.authenticateUser(userId, apiKey);

		TinyUserResponse tinyUserResponse = tinyUserService.deleteUser(tinyUserRequest, userId, apiKey);
		log.info("The response prepared for the request: {}", tinyUserResponse);

		return new ResponseEntity<>(tinyUserResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/v1/tiny/user")
	public ResponseEntity<List<TinyUrlResponse>> getAllTinyUrlsByUser(@RequestHeader("user-id") int userId, @RequestHeader("api-key") String apiKey) {
		log.info("Request received to get all tiny urls by user {}", userId);

		authenticationService.authenticateUser(userId, apiKey);

		List<TinyUrlResponse> tinyUserResponse = tinyUserService.getAllByUserId(userId);
		log.info("The response prepared for the request: {}", tinyUserResponse);

		return new ResponseEntity<>(tinyUserResponse, HttpStatus.OK);

	}
}
