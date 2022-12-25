package io.jyotirmay.tiny.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.jyotirmay.tiny.entity.TinyUser;

@Repository
public interface TinyUserRepository extends JpaRepository<TinyUser, Integer>{

	Optional<TinyUser> findByTinyUserIdAndActive(int tinyUserId, boolean active);
	
	Optional<TinyUser> findByUsernameAndEmailAndActive(String username, String email, boolean active);
}
