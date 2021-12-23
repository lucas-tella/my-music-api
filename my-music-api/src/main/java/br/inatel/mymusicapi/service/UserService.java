package br.inatel.mymusicapi.service;

import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public void createNewUser(User user) {
		
		Optional<User> foundUser = repository.findById(user.getId());
		
		if (!foundUser.isPresent() && isEmailValid(user)) {

			repository.save(user);
		}
	}
	
	public boolean isEmailValid(User user) {
	
		Optional<User> existingEmail = repository.findByEmail(user.getEmail());
		
		if (!existingEmail.isPresent() && isEmailAddressValid(user)) {

			return true;
		}
		
		return false;
	}
	
	public boolean isEmailAddressValid(User user) {
		
	    boolean result = true;
	    
	    try {
	        InternetAddress emailAddr = new InternetAddress(user.getEmail());
	        emailAddr.validate();
	    } catch (AddressException e) {
	    	
	        result = false;
	    }
	    return result;
	}
}
