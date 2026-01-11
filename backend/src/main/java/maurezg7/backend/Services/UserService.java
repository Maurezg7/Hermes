package maurezg7.backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import maurezg7.backend.Services.impl.IUserService;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.User;
import maurezg7.backend.repository.UserRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		if(users.isEmpty()) {
			throw new ResourceNotFoundException("The users not found.");
		}
		return users;
	}
	
	@Override
	public User findById(Long id) {
		User user = this.userRepository.findById(id).orElse(null);
		if(user == null) {
			throw new ResourceNotFoundException("The user not found.");
		}
		return user;
	}

	@Override
	public User findByUser(String userdata) {
		User user = this.userRepository.userLoging(userdata);
		if(user == null) {
			throw new ResourceNotFoundException("The user is null.");
		}
		return user;
	}

	@Override
	public void addUser(User user) {
		User usernameSearched = this.userRepository.userLoging(user.getUsername());
		User emailSearched = this.userRepository.userLoging(user.getEmail());
		
		if(user.getPassword() == null || user.getUsername() == null || user.getEmail() == null) {
			throw new ResourceNotFoundException("The user is not complete.");
		}
		
		if(user == null) {
			throw new ResourceNotFoundException("The user is null.");
		}
		
		if(usernameSearched != null || emailSearched != null) {
			throw new ResourceNotFoundException("The user was created");
		}
		
		String encodedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		this.userRepository.save(user);
	}
	
	@Override
	public User updateUser(User userReceived, Long id) {
		User user = this.userRepository.findById(id).orElse(null);
		if(user == null) {
			throw new ResourceNotFoundException("The user not found.");
		}
		user.setUsername(userReceived.getUsername());
		user.setEmail(userReceived.getEmail());
		user.setPassword(userReceived.getPassword());
		this.userRepository.save(user);
		return user;
	}

	@Override
	public Boolean deleteUser(Long id) {
		User user = this.userRepository.findById(id).orElse(null);
		return user == null ? false : true;
	}
	
	
}
