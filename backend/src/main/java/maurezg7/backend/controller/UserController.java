package maurezg7.backend.controller;

import java.util.Map;
import java.util.List;
import maurezg7.backend.models.DTO.UserDTO;
import maurezg7.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
       this.userService = userService;
    }
    
    @GetMapping("/{userData}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userData){
        return ResponseEntity.ok(userService.getUser(userData));
    }
    
    @PutMapping("/{userData}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userData, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(userData, userDTO));
    } 
    
    @DeleteMapping("/{userData}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String userData){
        Map<String, String> response = userService.deleteUser(userData);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getId/{userData}")
    public ResponseEntity<Long> getUserId(@PathVariable String userData){
        return ResponseEntity.ok(userService.getUserId(userData));
    }
    
    @GetMapping("/search/{query}")
    public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable String query){
        return ResponseEntity.ok(userService.searchUsers(query));
    }
}
