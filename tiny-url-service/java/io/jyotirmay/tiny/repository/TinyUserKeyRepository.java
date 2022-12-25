package io.jyotirmay.tiny.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.jyotirmay.tiny.entity.TinyUserKey;

@Repository
public interface TinyUserKeyRepository extends JpaRepository<TinyUserKey, Integer> {

	Optional<TinyUserKey> findByTinyUserIdAndActive(int userId, boolean active);
}
