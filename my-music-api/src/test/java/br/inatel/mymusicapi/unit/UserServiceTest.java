package br.inatel.mymusicapi.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.UserDto;
import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.UserService;
import util.UserSample;

@SpringBootTest @ActiveProfiles("test")
class UserServiceTest {

	@Autowired
	UserService service;
	@MockBean
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
	@Test
	void shouldValidateUserEmail() {
		BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
	}
	@Test
	void shouldCreateNewUser() {
		UserDto user = service.createNewUser(
				UserSample.createUserSample("Test", "test@email.com", "12345678"));
		assertTrue(user.getEmail() != null);
	}
	
}