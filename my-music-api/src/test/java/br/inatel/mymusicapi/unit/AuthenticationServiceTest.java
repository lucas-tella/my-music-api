//package br.inatel.mymusicapi.unit;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.ActiveProfiles;
//
//import br.inatel.mymusicapi.model.User;
//import br.inatel.mymusicapi.service.AuthenticationService;
//
//@SpringBootTest @ActiveProfiles("test")
//public class AuthenticationServiceTest {
//
//	@Autowired
//	AuthenticationService service;
//	
//	@Test
//	void 
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Optional<User> user = repository.findByEmail(email);
//		if (user.isPresent()) {
//			return user.get();
//		}
//		throw new UsernameNotFoundException("Invalid data.");
//	}
//}
