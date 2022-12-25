package io.jyotirmay.tiny.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jyotirmay.tiny.entity.TinyUser;
import io.jyotirmay.tiny.entity.TinyUserKey;
import io.jyotirmay.tiny.exception.UserNotAuthorizedException;
import io.jyotirmay.tiny.exception.UserNotFoundException;
import io.jyotirmay.tiny.repository.TinyUserKeyRepository;
import io.jyotirmay.tiny.repository.TinyUserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private TinyUserRepository tinyUserRepository;

	@Autowired
	private TinyUserKeyRepository tinyUserKeyRepository;
	
	public void authenticateUser(int userId, String apiKey) {
		Optional<TinyUser> optionalTinyUser = tinyUserRepository.findByTinyUserIdAndActive(userId, true);

		if (!optionalTinyUser.isPresent())
			throw new UserNotFoundException();

		Optional<TinyUserKey> optionalTinyUserKey = tinyUserKeyRepository.findByTinyUserIdAndActive(userId, true);

		if (!optionalTinyUserKey.isPresent())
			throw new UserNotFoundException();

		String encodedApiKey = Base64.getEncoder().withoutPadding().encodeToString(apiKey.getBytes());
		if(!encodedApiKey.equals(optionalTinyUserKey.get().getApiKey()))
			throw new UserNotAuthorizedException();
	}
}
