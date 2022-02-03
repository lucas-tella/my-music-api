package br.inatel.mymusicapi.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.UserService;

@SpringBootTest @ActiveProfiles("test")
class UserServiceTest {

	@Autowired
	UserService service;
	@Autowired
	UserRepository userRepository;
	
	@Test
	void shouldValidateEmailAdress() {
		NewUserDto user = new NewUserDto("Test", "qa@test.com", "12345678");
		assertTrue(service.isEmailAddressValid(user));
	}
	@Test
	void shouldNotValidateEmailAdressWithoutA() {
		NewUserDto user = new NewUserDto("Test", "testtest.com", "12345678");
		assertFalse(service.isEmailAddressValid(user));
	}
}