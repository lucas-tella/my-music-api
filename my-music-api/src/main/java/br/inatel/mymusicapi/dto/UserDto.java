package br.inatel.mymusicapi.dto;

import br.inatel.mymusicapi.model.User;
import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String name;
	private String email;
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getUserName();
		this.email = user.getEmail();
	}
	public UserDto() {}
}
