package io.jyotirmay.tiny.util;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ApplicationUtil {

	public static String generateRandomAlphaNumericString(int length) {
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		log.info("The random string generated for length {} is {}", length, generatedString);
		return generatedString;
	}

}
