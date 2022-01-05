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

import br.inatel.mymusicapi.dto.ErrorDto;
import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.service.UserService;

@RestController
@RequestMapping (value = "/users")
public class UserController {

	@Autowired 
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody @Valid NewUserDto dto) {

		if (userService.isEmailValid(dto)) {
			
			UserDto newUser = userService.createNewUser(dto);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(
				403, "This email is already being used."));
	}
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {

		UserDto dto = userService.getUser(id);
		
		if (!(dto == null)) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id " + id + " not found."));
	}
	
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		
		Long deletedUserId = userService.deleteUser(id);

		if (!(deletedUserId==null)) {
			
			return ResponseEntity.status(HttpStatus.OK).body("User " + id + " deleted.");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "User " + id + " not found."));
	}
	
	@GetMapping("/{id}/playlists")
	public ResponseEntity<?> listUserPlaylists(@PathVariable("id") Long id) {
		
		List<Playlist> playlists = userService.getUserPlaylists(id);
		
		if (!(playlists == null)) {

			return ResponseEntity.status(HttpStatus.OK).body(playlists);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "User " + id + " not found."));
	}
	
//	@GetMapping()
//	public ResponseEntity<?> test() {
//		
//		HttpRequest request = null;
//		try {
//			request = HttpRequest.newBuilder()
//					.uri(new URI ("https://deezerdevs-deezer.p.rapidapi.com/infos"))
//					.header("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
//					.header("x-rapidapi-key", "5982032ab7msh004d403d030bf5bp1713cfjsna7173a3b9528")
//					.method("GET", HttpRequest.BodyPublishers.noBody())
//					.build();
//		} catch (URISyntaxException e) {
//			
//			e.printStackTrace();
//		}
//		HttpResponse<String> response = null;
//		try {
//			// tentar utilizar WebClient;
//			response = HttpClient.newHttpClient().send(
//					request, HttpResponse.BodyHandlers.ofString());
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
//		System.out.println(response.body());
//		
//		return null;
//		
//	}
}
