package maurezg7.backend.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import maurezg7.backend.models.User;
import maurezg7.backend.repository.UserRepository;

public interface IUserService {
	List<User> getAllUsers();
	User findByUser(String userdata);
	User findById(Long id);
	void addUser(User user);
	User updateUser(User userReceived, Long id);
	Boolean deleteUser(Long id);
}
