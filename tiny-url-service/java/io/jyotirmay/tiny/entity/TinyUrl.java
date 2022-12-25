package io.jyotirmay.tiny.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tiny_url")
@Builder
public class TinyUrl {

	@Id
	@Column(name = "tiny_url")
	private String tinyUrl;
	
	@Column(name = "actual_url")
	private String actualUrl;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
}
