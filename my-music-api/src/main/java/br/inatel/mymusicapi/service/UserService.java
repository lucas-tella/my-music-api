package br.inatel.mymusicapi.service;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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

	private final UserRepository repository;

	public UserDto createNewUser(NewUserDto dto) {

		User user = User.builder()
				.password(dto.getPassword())
				.email(dto.getEmail())
				.userName(dto.getName())
				.build();

		Optional<User> foundUser = repository.findById(user.getId());

		if (!foundUser.isPresent()) {

			User newUser = repository.save(user);

			return UserDto.builder()
					.id(newUser.getId())
					.email(newUser.getEmail())
					.name(newUser.getUserName())
					.build();
		}

		return null; // tratar exception
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

			User foundUser = user.get();

			return UserDto.builder()
					.email(foundUser.getEmail())
					.id(foundUser.getId())
					.name(foundUser.getUserName())
					.build();
		}

		return null; // tratar exception
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
