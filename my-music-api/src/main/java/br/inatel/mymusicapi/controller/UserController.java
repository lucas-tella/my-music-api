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

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping (value = "/users")
public class UserController {
	
	@Autowired 
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody @Valid NewUserDto dto) {
			userService.validateNewUser(dto);
			UserDto newUser = userService.createNewUser(dto);
			log.info("New user successfuly created.");
			return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		UserDto dto = userService.getUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		log.info("User successfuly deleted.");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}