package io.jyotirmay.tiny.user.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jyotirmay.tiny.user.entity.TinyUserEntity;
import io.jyotirmay.tiny.user.exception.TinyUserException;
import io.jyotirmay.tiny.user.model.TinyUser;
import io.jyotirmay.tiny.user.repository.TinyUserRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TinyUserService {

	private TinyUserRepository tinyUserRepository;
	
	public TinyUserEntity addUser(TinyUser tinyUser) {
		
		TinyUserEntity tinyUserEntity = TinyUserEntity.builder()
				.userId(UUID.randomUUID())
				.name(tinyUser.getName())
				.emailId(tinyUser.getEmailId())
				.active(true)
				.build();
		
		return tinyUserRepository.save(tinyUserEntity);
		
	}
	
	public void deleteUser(UUID username) throws TinyUserException {
		
		Optional<TinyUserEntity> optionalTinyUserEntity = tinyUserRepository.findById(username);
		
		if(optionalTinyUserEntity.isPresent()) {
			TinyUserEntity tinyUserEntity = optionalTinyUserEntity.get();
			tinyUserEntity.setActive(false);
			tinyUserRepository.save(tinyUserEntity);
		}else {
			throw new TinyUserException("The user is either not present or already removed earlier.");
		}
	}
}
