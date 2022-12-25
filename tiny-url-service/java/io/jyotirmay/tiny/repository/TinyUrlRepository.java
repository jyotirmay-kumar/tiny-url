package io.jyotirmay.tiny.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.jyotirmay.tiny.entity.TinyUrl;

@Repository
public interface TinyUrlRepository extends JpaRepository<TinyUrl, String>{

	Optional<TinyUrl> findByTinyUrlAndActive(String tinyUrl, boolean active);
	
	List<TinyUrl> findAllByUserId(int userId);
}
