package maurezg7.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import maurezg7.backend.Services.UserService;
import maurezg7.backend.models.User;

@RestController
@RequestMapping("hermes-app") //http://localhost:8080/hermes-app
@CrossOrigin(value = "http://localhost:4200") // Default port angular.
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		List<User> users = this.userService.getAllUsers();
		return users;
	}
	
	@GetMapping("/users/{userdata}")
	public ResponseEntity<User> getFindUser(@PathVariable String userdata) {
		User user = this.userService.findByUser(userdata);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/users/saveuser")
	public void addUser(@RequestBody User user) {
		this.userService.addUser(user);
	}
	
	@PutMapping("/users/updateuser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userReceived) {
		User user = this.userService.updateUser(userReceived, id);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/users/deleteuser/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
		Boolean user = this.userService.deleteUser(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", user);
        return ResponseEntity.ok(response);
	}
}
