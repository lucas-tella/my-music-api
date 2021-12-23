package br.inatel.mymusicapi.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.mymusicapi.dao.UserDao;
import br.inatel.mymusicapi.dto.ErrorDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private UserService userService;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postUser(@RequestBody @Valid UserDto dto) {

		User newUser = dto.convertToUser();
		
		if (userService.isEmailValid(newUser)) {
			
			userService.createNewUser(newUser);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(new UserDao(newUser));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(
				403, "This email is already being used."));
	}
	
	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<?> getUserById(@PathVariable String id) {

		Optional<User> user = userRepository.findById(id);

		if (user.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(new UserDao (user.get()));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id " + id + " not found."));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deleteUser(User user) {
		
		String id = user.getId();
		Optional<User> genericUser = userRepository.findById(user.getId());

		if (genericUser.isPresent()) {
			
			userRepository.deleteById(user.getId());
			
			return ResponseEntity.status(HttpStatus.OK).body("User " + id + " deleted.");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "User not found."));
	}
	
	@GetMapping("/{id}/playlists")
	@Cacheable(value = "userPlaylists")
	public ResponseEntity<?> listUserPlaylists(@PathVariable("id") String id) {
		
		Optional<User> user = userRepository.findById(id);

		if (user.isPresent()) {
			List<Playlist> playlists = user.get().getMyPlaylists();

			return ResponseEntity.status(HttpStatus.OK).body(playlists);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "User not found."));
	}
	
	@GetMapping()
	public ResponseEntity<?> test() {
		
		HttpRequest request = null;
		try {
			request = HttpRequest.newBuilder()
					.uri(new URI ("https://deezerdevs-deezer.p.rapidapi.com/infos"))
					.header("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
					.header("x-rapidapi-key", "5982032ab7msh004d403d030bf5bp1713cfjsna7173a3b9528")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		HttpResponse<String> response = null;
		try {
			// tentar utilizar WebClient;
			response = HttpClient.newHttpClient().send(
					request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println(response.body());
		
		return null;
		
	}
}
