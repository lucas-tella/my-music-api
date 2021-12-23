package br.inatel.mymusicapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.inatel.mymusicapi.model.User;

public class UserDto {
	
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

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public User convertToUser() {
		return new User(name, email, password);
	}
}
