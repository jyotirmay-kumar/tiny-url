package io.jyotirmay.tiny.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jyotirmay.tiny.dto.TinyUrlResponse;
import io.jyotirmay.tiny.entity.TinyUrl;
import io.jyotirmay.tiny.entity.TinyUrlStore;
import io.jyotirmay.tiny.exception.UrlNotFoundException;
import io.jyotirmay.tiny.exception.UrlStoreEmptyException;
import io.jyotirmay.tiny.exception.UserNotAuthorizedException;
import io.jyotirmay.tiny.repository.TinyUrlRepository;
import io.jyotirmay.tiny.repository.TinyUrlStoreRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TinyUrlService {

	@Autowired
	private TinyUrlRepository tinyUrlRepository;

	@Autowired
	private TinyUrlStoreRepository tinyUrlStoreRepository;
	
	public String getActualUrl(String tinyUrl) {

		Optional<TinyUrl> optionalTinyUrl = tinyUrlRepository.findByTinyUrlAndActive(tinyUrl, true);
		if (!optionalTinyUrl.isPresent()) {
			log.error("The tiny url {} is not available in store.", tinyUrl);
			throw new UrlNotFoundException();
		}

		return optionalTinyUrl.get().getActualUrl();
	}

	@Transactional
	public TinyUrlResponse createTinyUrl(String actualUrl, int userId, String apiKey) {

		Optional<TinyUrlStore> optionalFirstKey = tinyUrlStoreRepository.findFirstByUsedOrderByCreatedOnAsc(false);

		if (!optionalFirstKey.isPresent())
			throw new UrlStoreEmptyException();

		TinyUrlStore tinyUrlStore = optionalFirstKey.get();
		tinyUrlStore.setUsed(true);
		tinyUrlStore.setUpdatedOn(new Date());
		tinyUrlStoreRepository.save(tinyUrlStore);

		TinyUrl tinyUrl = TinyUrl.builder()
				.tinyUrl(tinyUrlStore.getTinyUrl())
				.active(true)
				.actualUrl(actualUrl)
				.userId(userId)
				.createdOn(new Date())
				.build();
		
		tinyUrl = tinyUrlRepository.save(tinyUrl);
		
		TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
				.message("Tiny URL successfully created for " +  userId)
				.tinyUrl("http://localhost:8080/" + tinyUrl.getTinyUrl())
				.build();
		
		return tinyUrlResponse;
	}
	
	public TinyUrlResponse deleteTinyUrl(String tinyUrlString, int userId, String apiKey) {
		
		Optional<TinyUrl> optionalTinyUrl = tinyUrlRepository.findByTinyUrlAndActive(tinyUrlString, true);
		if (!optionalTinyUrl.isPresent()) {
			log.error("The tiny url {} is not available in store.", tinyUrlString);
			throw new UrlNotFoundException();
		}
		
		TinyUrl tinyUrl = optionalTinyUrl.get();
		if(tinyUrl.getUserId() != userId) {
			log.error("The user {} is not authorized to delete {} url.", userId, tinyUrlString);
			throw new UserNotAuthorizedException();
		}
		
		tinyUrl.setActive(false);
		tinyUrl.setUpdatedOn(new Date());
		
		tinyUrl = tinyUrlRepository.save(tinyUrl);
		log.info("The tiny url is deleted: {}", tinyUrl);
		
		TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
				.message("Tiny URL successfully deleted for user:" +  userId)
				.tinyUrl(tinyUrlString)
				.build();
		
		return tinyUrlResponse;
	}

	
}
