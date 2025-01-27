package io.jyotirmay.tiny.user.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jyotirmay.tiny.user.entity.TinyUserEntity;
import io.jyotirmay.tiny.user.exception.TinyUserException;
import io.jyotirmay.tiny.user.model.TinyUser;
import io.jyotirmay.tiny.user.service.TinyUserService;
import io.jyotirmay.tiny.user.util.TinyUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class TinyUserController {

	private TinyUserService tinyUserService;

	@PostMapping(value = "/v1/user/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TinyUserEntity> registerUser(@RequestBody String requestString) {

		TinyUser tinyUser = TinyUtil.parseTinyUser(requestString);
		log.info("Request received to register a user:: {}", tinyUser.getName());
		TinyUserEntity tinyUserEntity = tinyUserService.addUser(tinyUser);
		log.info("Registered user {} with id: {}", tinyUserEntity.getName(), tinyUserEntity.getUserId());
		return new ResponseEntity<TinyUserEntity>(tinyUserEntity, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/v1/user/unregister", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void unregisterUser(@RequestBody String username) {

		try {
			UUID userUUID = UUID.fromString(username);
			log.info("Request received to register a user:: {}", userUUID);
			tinyUserService.deleteUser(userUUID);
			log.info("User {} successfully unregistered.", userUUID);
		} catch (IllegalArgumentException e) {
			throw new TinyUserException("Incorrect username.");
		}
	}
}
