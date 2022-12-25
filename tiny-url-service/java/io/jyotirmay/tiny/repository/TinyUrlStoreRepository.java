package io.jyotirmay.tiny.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.jyotirmay.tiny.entity.TinyUrlStore;

@Repository
public interface TinyUrlStoreRepository extends JpaRepository<TinyUrlStore, String>{

	Optional<TinyUrlStore> findFirstByUsedOrderByCreatedOnAsc(Boolean used);
	
	int countByUsed(boolean used);
}
