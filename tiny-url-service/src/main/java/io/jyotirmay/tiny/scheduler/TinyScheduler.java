package io.jyotirmay.tiny.scheduler;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jyotirmay.tiny.entity.TinyUrlStore;
import io.jyotirmay.tiny.repository.TinyUrlStoreRepository;
import io.jyotirmay.tiny.util.ApplicationUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RefreshScope
public class TinyScheduler {

	@Autowired
	private Environment env;
	
	@Autowired
	private TinyUrlStoreRepository tinyUrlStoreRepository;
	
	@Scheduled(fixedRateString = "${scheduler-rate}")
	public void scheduleKeyGeneration() {
		
		log.info("The scheduler to refill the keystore started execution at {}", Instant.now());
		int deltaRequired = Integer.parseInt(env.getProperty("application.key-generator.delta"));
		
		int availableKeys = tinyUrlStoreRepository.countByUsed(false);
		
		int keysRequired = deltaRequired - availableKeys;
		
		while(keysRequired > 0) {
			
			tinyUrlStoreRepository.save(createTinyUrls());
			keysRequired--;
		}
		log.info("The scheduler to refill the keystore finished execution at {}", Instant.now());
	}
	
	private TinyUrlStore createTinyUrls() {
		
		int tinyUrlLength = Integer.parseInt(env.getProperty("application.tiny-url.length"));
		String tinyUrl = ApplicationUtil.generateRandomAlphaNumericString(tinyUrlLength);
		
		TinyUrlStore tinyUrlStore = TinyUrlStore.builder()
				.tinyUrl(tinyUrl)
				.used(false)
				.createdOn(new Date())
				.build();
		
		return tinyUrlStore;
	}
	
}
