package util;

import br.inatel.mymusicapi.dto.NewUserDto;

public class UserSample {

	public static NewUserDto createUserSample(String name, String email, String password) {
		NewUserDto user = new NewUserDto(name, email, password);
		return user;
	}
}
