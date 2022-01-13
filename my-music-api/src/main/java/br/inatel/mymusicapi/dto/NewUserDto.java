package br.inatel.mymusicapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class NewUserDto {
	
	@NotNull
	@NotEmpty
	@Length(min = 4)
	private String name;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotEmpty
	@Length(min = 8)
	private String password;
	
	public NewUserDto(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
