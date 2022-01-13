package br.inatel.mymusicapi.service;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private final UserRepository repository;

	public UserDto createNewUser(NewUserDto dto) {
		if(isEmailValid(dto)) {
			User user = new User(dto);
			User newUser = repository.save(user);
			return new UserDto(newUser);
		}
		return null;
	}
	
	public boolean isPasswordValid(NewUserDto dto) {
		if (dto.getPassword().toString().length()==8) {
			return true;
		}
		return false;
	}

	public boolean isEmailValid(NewUserDto dto) {
		Optional<User> existingEmail = repository.findByEmail(dto.getEmail());
		if (!existingEmail.isPresent() && isEmailAddressValid(dto)) {
			return true;
		}
		return false;
	}

	public boolean isEmailAddressValid(NewUserDto dto) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(dto.getEmail());
			emailAddr.validate();
		} catch (AddressException e) {
			result = false;
		}
		return result;
	}

	public Long deleteUser(Long id) {
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			repository.deleteById(id);
			return user.get().getId();
		}
		return null;
	}

	public UserDto getUser(Long id) {
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			return new UserDto(user.get());
		}
//		throw new RuntimeException(); // tratar exception
		return null; // trocar por exception tratada
	}

	public List<Playlist> getUserPlaylists (Long id) {
		Optional<User> user = repository.findById(id);
		if(user.isPresent()) {
			User foundUser = user.get();
			return foundUser.getPlaylists();
		}
		return null;
	}
}
