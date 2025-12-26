package maudev.backend.service.impl;

import maudev.backend.model.entity.Role;
import maudev.backend.model.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    Object findByUsername(String username);
    Object findByEmail(String email);
    User saveUser(User user);
    void deleteByUsername(String username);
    void deleteByEmail(String email);
    Role saveRole(Role role);
    void addRoleToUser(String  username, String roleName);
}
