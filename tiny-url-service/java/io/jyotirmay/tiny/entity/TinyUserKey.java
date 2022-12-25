package io.jyotirmay.tiny.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tiny_user_key")
@Builder
public class TinyUserKey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tiny_user_key_id")
	private int tinyUserKeyId;
	
	@Column(name = "tiny_user_id")
	private int tinyUserId;
	
	@Column(name = "api_key")
	private String apiKey;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
}
