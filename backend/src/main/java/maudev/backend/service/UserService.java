package maudev.backend.service;

import maudev.backend.model.entity.Role;
import maudev.backend.model.entity.User;
import maudev.backend.repository.RoleRepository;
import maudev.backend.repository.UserRepository;
import maudev.backend.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        this.userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        this.userRepository.deleteByEmail(email);
    }

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, String roleName) {
        User user = this.userRepository.findByUsername(username);
        Role role = this.roleRepository.findByName(roleName);

        if (user != null && role != null) {
            user.getRoles().add(role);
            this.userRepository.save(user);
        }
    }
}