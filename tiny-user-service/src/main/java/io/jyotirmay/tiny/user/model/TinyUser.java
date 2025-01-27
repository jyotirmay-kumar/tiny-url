package io.jyotirmay.tiny.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TinyUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fistName;
	
	private String lastName;

	private String emailId;
}
