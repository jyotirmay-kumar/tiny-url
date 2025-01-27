package io.jyotirmay.tiny.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jyotirmay.tiny.user.exception.TinyClientException;
import io.jyotirmay.tiny.user.model.TinyUser;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TinyUtil {

	public static final ObjectMapper MAPPER = new ObjectMapper();

	public static TinyUser parseTinyUser(String str) {

		try {
			return MAPPER.convertValue(str, TinyUser.class);
		} catch (Exception e) {
			log.error("Exception occurred while parsing the json string to tiny user object", e.getMessage());
			throw new TinyClientException("Error occurred in parsing request string");
		}
	}
}
