package br.inatel.mymusicapi.service;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private final UserRepository repository;

	public UserDto createNewUser(NewUserDto dto) {
		if(isEmailValid(dto)) {
			User user = new User(dto);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			User newUser = repository.save(user);
			log.info("New user added to database repository.");
			return new UserDto(newUser);
		}
		return null;
	}
	
	public boolean isEmailValid(NewUserDto dto) {
		Optional<User> existingEmail = repository.findByEmail(dto.getEmail());
		log.info("Validating email.");
		if (!existingEmail.isPresent() && isEmailAddressValid(dto)) {
			log.info("Email is valid.");
			return true;
		}
		log.info("Email is invalid.");
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
	
	public boolean isPasswordValid(NewUserDto dto) {
		if (dto.getPassword().toString().length()==8) {
			log.info("Validating password.");
			return true;
		}
		return false;
	}

	public Long deleteUser(Long id) {
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			repository.deleteById(id);
			log.info("User deleted from database repository.");
			return user.get().getId();
		}
		return null;
	}

	public UserDto getUser(Long id) {
		Optional<User> user = repository.findById(id);
		log.info("Searching for user in repository...");
		if (user.isPresent()) {
			return new UserDto(user.get());
		}
//		throw new RuntimeException(); // tratar exception
		return null; // trocar por exception tratada
	}

	public List<Playlist> getUserPlaylists (Long id) {
		Optional<User> user = repository.findById(id);
		log.info("Searching for user playlist's in repository...");
		if(user.isPresent()) {
			User foundUser = user.get();
			return foundUser.getPlaylists();
		}
		return null;
	}
}
