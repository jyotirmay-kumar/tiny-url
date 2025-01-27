package io.jyotirmay.tiny.user.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tiny_user")
public class TinyUserEntity {

	@Id
	private UUID userId;

	private String name;

	private String emailId;

	private boolean active;

}
