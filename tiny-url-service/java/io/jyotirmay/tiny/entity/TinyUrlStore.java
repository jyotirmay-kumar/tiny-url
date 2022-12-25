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
@Entity(name = "tiny_url_store")
@Builder
public class TinyUrlStore {

	@Id
	@Column(name = "tiny_url")
	private String tinyUrl;
	
	@Column(name = "used")
	private boolean used;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
}
