package io.jyotirmay.tiny.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jyotirmay.tiny.dto.TinyUrlResponse;
import io.jyotirmay.tiny.dto.TinyUserRequest;
import io.jyotirmay.tiny.dto.TinyUserResponse;
import io.jyotirmay.tiny.entity.TinyUrl;
import io.jyotirmay.tiny.entity.TinyUser;
import io.jyotirmay.tiny.entity.TinyUserKey;
import io.jyotirmay.tiny.exception.UserAlreadyExistException;
import io.jyotirmay.tiny.exception.UserNotFoundException;
import io.jyotirmay.tiny.repository.TinyUrlRepository;
import io.jyotirmay.tiny.repository.TinyUserKeyRepository;
import io.jyotirmay.tiny.repository.TinyUserRepository;
import io.jyotirmay.tiny.util.ApplicationUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TinyUserService {

	@Autowired
	private TinyUserRepository tinyUserRepository;

	@Autowired
	private TinyUserKeyRepository tinyUserKeyRepository;

	@Autowired
	private TinyUrlRepository tinyUrlRepository;

	@Autowired
	private Environment env;

	@Transactional
	public TinyUserResponse createUser(TinyUserRequest tinyUser) {

		Optional<TinyUser> existingUser = tinyUserRepository.findByUsernameAndEmailAndActive(tinyUser.getUsername(), tinyUser.getEmail(), true);

		if (existingUser.isPresent())
			throw new UserAlreadyExistException();

		TinyUser user = TinyUser.builder().username(tinyUser.getUsername()).email(tinyUser.getEmail()).active(true).createdOn(new Date()).build();

		user = tinyUserRepository.save(user);
		log.info("The user row added to user store is {}", user);

		int apiKeySize = Integer.parseInt(env.getProperty("application.api-key.size"));
		String apiKey = ApplicationUtil.generateRandomAlphaNumericString(apiKeySize);
		String encodedApiKey = Base64.getEncoder().withoutPadding().encodeToString(apiKey.getBytes());

		TinyUserKey userKey = TinyUserKey.builder().apiKey(encodedApiKey).tinyUserId(user.getTinyUserId()).createdOn(new Date()).active(true).build();

		userKey = tinyUserKeyRepository.save(userKey);
		log.info("The user key row added to user store is {}", userKey);

		TinyUserResponse tinyUserResponse = TinyUserResponse.builder().message("User successfully created for " + user.getUsername()).apiKey(apiKey).userId(user.getTinyUserId()).build();

		return tinyUserResponse;
	}

	@Transactional
	public TinyUserResponse deleteUser(TinyUserRequest tinyUserRequest, int userId, String apiKey) {

		Optional<TinyUser> optionalTinyUser = tinyUserRepository.findByUsernameAndEmailAndActive(tinyUserRequest.getUsername(), tinyUserRequest.getEmail(), true);

		if (!optionalTinyUser.isPresent())
			throw new UserNotFoundException();

		Optional<TinyUserKey> optionalTinyUserKey = tinyUserKeyRepository.findByTinyUserIdAndActive(optionalTinyUser.get().getTinyUserId(), true);

		if (optionalTinyUserKey.isPresent()) {
			TinyUserKey tinyUserKey = optionalTinyUserKey.get();
			tinyUserKey.setActive(false);
			tinyUserKey.setUpdatedOn(new Date());
			tinyUserKeyRepository.save(tinyUserKey);
		}

		TinyUser tinyUser = optionalTinyUser.get();
		tinyUser.setActive(false);
		tinyUser.setUpdatedOn(new Date());

		tinyUserRepository.save(tinyUser);
		log.info("The user {} is deactivated.", tinyUser.getUsername());

		TinyUserResponse tinyUserResponse = TinyUserResponse.builder().message("User successfully deleted for " + tinyUser.getUsername()).userId(tinyUser.getTinyUserId()).build();

		return tinyUserResponse;
	}

	public List<TinyUrlResponse> getAllByUserId(int userId) {
		List<TinyUrl> tinyUrls = tinyUrlRepository.findAllByUserId(userId);
		
		if(null == tinyUrls || tinyUrls.isEmpty())
			return null;
		
		return tinyUrls.stream().map(tinyUrl -> 
			TinyUrlResponse.builder()
				.tinyUrl(tinyUrl.getTinyUrl())
				.actualUrl(tinyUrl.getActualUrl())
				.active(tinyUrl.isActive())
				.build()
		).collect(Collectors.toList());
	}

}
