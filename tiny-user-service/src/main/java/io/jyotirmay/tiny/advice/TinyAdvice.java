package io.jyotirmay.tiny.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jyotirmay.tiny.dto.TinyUrlResponse;
import io.jyotirmay.tiny.exception.UserAlreadyExistException;
import io.jyotirmay.tiny.exception.UserNotAuthorizedException;
import io.jyotirmay.tiny.exception.UserNotFoundException;

@ControllerAdvice
public class TinyAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<TinyUrlResponse> handleUserNotFoundException(WebRequest request, UserNotFoundException e) {
		TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
				.message("User not found in store.")
				.build();
		return new ResponseEntity<>(tinyUrlResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserNotAuthorizedException.class)
	public ResponseEntity<TinyUrlResponse> handleUserNotAuthorizedException(WebRequest request, UserNotAuthorizedException e) {
		TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
				.message("User is not authorized for this operation.")
				.build();
		return new ResponseEntity<>(tinyUrlResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<TinyUrlResponse> handleUserAlreadyExistException(WebRequest request, UserNotAuthorizedException e) {
		TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
				.message("Active user with this username and email already exists.")
				.build();
		return new ResponseEntity<>(tinyUrlResponse, HttpStatus.BAD_REQUEST);
	}
}
