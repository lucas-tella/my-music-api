package br.inatel.mymusicapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.inatel.mymusicapi.service.UserService;

@RestController
@RequestMapping (value = "/users")
public class UserController {
	@Autowired 
	private UserService userService;
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody @Valid NewUserDto dto) {
		if (userService.isEmailValid(dto)) {
			if(userService.isPasswordValid(dto)) {
				UserDto newUser = userService.createNewUser(dto);
				return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
					new ErrorDto(403, "The password must have 8 characters."));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
				new ErrorDto(403, "Invalid email."));
	}
	@GetMapping(path="/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		UserDto dto = userService.getUser(id);
		if (!(dto == null)) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id '" + id + "' not found."));
	}
	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Long deletedUserId = userService.deleteUser(id);
		if (!(deletedUserId==null)) {
			return ResponseEntity.status(HttpStatus.OK).body("User " + id + " deleted.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User " + id + " not found."));
	}
}