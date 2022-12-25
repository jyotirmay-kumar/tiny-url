package io.jyotirmay.tiny.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(hidden = true)
public class TinyUrlCreateRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String actualUrl;
	
}
