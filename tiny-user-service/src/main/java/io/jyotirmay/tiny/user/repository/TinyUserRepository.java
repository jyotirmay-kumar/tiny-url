package io.jyotirmay.tiny.user.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.jyotirmay.tiny.user.entity.TinyUserEntity;

@Repository
public interface TinyUserRepository extends CrudRepository<TinyUserEntity, UUID> {

}
