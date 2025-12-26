package maudev.backend.controller;

import maudev.backend.exception.ResourceNotFoundException;
import maudev.backend.model.entity.User;
import maudev.backend.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api") // URL: http://localhost:8080/api/
@CrossOrigin(value = "http://localhost:4200") // Angular port
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<User> getUsers() {
        return ResponseEntity.ok().body(this.userService.findAll()).getBody();
    }

    @PostMapping("/role/save")
    public User createUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(this.userService.saveUser(user)).getBody();
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>  saveRole(@RequestBody RoleToUserForm form) {
        this.userService.addRoleToUser(form.getName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    class RoleToUserForm{
        private String name;
        private String roleName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = this.userService.findByUsername(username);
        if(user == null){
            throw new ResourceNotFoundException("The id was not found: " + username);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = this.userService.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("The id was not found: " + email);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<User> updateUserByUsername(@PathVariable("username") String username, @RequestBody User userReceived) {
        User user = this.userService.findByUsername(username);
        user.setUsername(userReceived.getUsername());
        user.setEmail(userReceived.getEmail());
        user.setPassword(userReceived.getPassword());
        this.userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<User> updateUserByEmail(@PathVariable("email") String email, @RequestBody User userReceived) {
        User user = this.userService.findByUsername(email);
        user.setUsername(userReceived.getUsername());
        user.setEmail(userReceived.getEmail());
        user.setPassword(userReceived.getPassword());
        this.userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String username) {
        User user = this.userService.findByUsername(username);
        if(user != null){
            throw new ResourceNotFoundException("The username was not found: " + username);
        }
        this.userService.deleteByUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
