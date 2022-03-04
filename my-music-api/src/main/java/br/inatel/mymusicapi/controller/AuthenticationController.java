package br.inatel.mymusicapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.mymusicapi.dto.LoginDto;
import br.inatel.mymusicapi.dto.TokenDto;
import br.inatel.mymusicapi.service.TokenService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/login")
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenService tokenService;
	@PostMapping
	public ResponseEntity<TokenDto> authentication(@RequestBody @Valid LoginDto dto){
		UsernamePasswordAuthenticationToken loginData = dto.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			log.info("Invalid credentials.");
			return ResponseEntity.badRequest().build();
		}
	}
}