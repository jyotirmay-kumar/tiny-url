package io.jyotirmay.tiny.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jyotirmay.tiny.dto.TinyUrlCreateRequest;
import io.jyotirmay.tiny.dto.TinyUrlDeleteRequest;
import io.jyotirmay.tiny.dto.TinyUrlResponse;
import io.jyotirmay.tiny.exception.UrlNotFoundException;
import io.jyotirmay.tiny.service.AuthenticationService;
import io.jyotirmay.tiny.service.TinyUrlService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Tag(name = "Tiny Url v1.0")
@OpenAPIDefinition(info = @Info(title = "Tiny Url", version = "1.0"))
public class TinyUrlController {

	@Autowired
	private TinyUrlService tinyUrlService;
	
	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping("/{tinyUrl}")
	public void redirectActualUrl(@PathVariable("tinyUrl") String tinyUrl, HttpServletResponse response)
			throws IOException {

		try {
			log.info("Request received to redirect to actual url for {}", tinyUrl);
			String actualUrl = tinyUrlService.getActualUrl(tinyUrl);

			response.sendRedirect(actualUrl);
		} catch (IOException io) {
			log.error("Exception occurred while redirecting to actual url for {}, ", tinyUrl, io);
			throw new UrlNotFoundException();
		}
	}
	
	@PostMapping(value = "/v1/tiny/url", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TinyUrlResponse> createTinyUrl(@RequestHeader("user-id") int userId, @RequestHeader("api-key") String apiKey, @RequestBody TinyUrlCreateRequest tinyUrlRequest) {
		
		log.info("Request received to create tiny url for {} for {}", tinyUrlRequest.getActualUrl(), userId);
		
		authenticationService.authenticateUser(userId, apiKey);
		
		TinyUrlResponse tinyUrlResponse = tinyUrlService.createTinyUrl(tinyUrlRequest.getActualUrl(), userId, apiKey);
		
		log.info("The response prepared for the request: {}", tinyUrlResponse);
		return new ResponseEntity<>(tinyUrlResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/v1/tiny/url", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TinyUrlResponse> deleteTinyUrl(@RequestHeader("user-id") int userId, @RequestHeader("api-key") String apiKey, @RequestBody TinyUrlDeleteRequest tinyUrlRequest) {
		
		log.info("Request received to delete tiny url for {} for {}", tinyUrlRequest.getTinyUrl(), userId);
		
		authenticationService.authenticateUser(userId, apiKey);
		
		TinyUrlResponse tinyUrlResponse = tinyUrlService.deleteTinyUrl(tinyUrlRequest.getTinyUrl(), userId, apiKey);
		
		log.info("The response prepared for the request: {}", tinyUrlResponse);
		return new ResponseEntity<>(tinyUrlResponse, HttpStatus.OK);
	}
}
