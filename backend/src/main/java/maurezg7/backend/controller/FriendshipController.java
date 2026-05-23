package maurezg7.backend.controller;

import java.util.Collections;
import java.util.List;
import maurezg7.backend.models.DTO.FriendDTO;
import maurezg7.backend.models.DTO.MessageDTO;
import maurezg7.backend.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "http://localhost:4200")
public class FriendshipController {
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/accepted/{userId}")
    public ResponseEntity<List<FriendDTO>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getAcceptedFriends(userId));
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<FriendDTO>> getPending(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getPendingRequests(userId));
    }

    @PostMapping("/block/{userId}/{targetId}")
    public ResponseEntity<?> block(@PathVariable Long userId, @PathVariable Long targetId) {
        friendshipService.blockUser(userId, targetId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario bloqueado"));
    }
    
    @PostMapping("/request/{userId}/{friendId}")
    public ResponseEntity<?> requestFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendshipService.sendFriendRequest(userId, friendId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Solicitud enviada"));
    }

    @GetMapping("/messages/{userId}/{friendId}")
    public ResponseEntity<List<MessageDTO>> getChat(
            @PathVariable Long userId, 
            @PathVariable Long friendId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(friendshipService.getPrivateMessages(userId, friendId, page, size));
    }
    
    @PutMapping("/accept/{userId}/{friendId}")
    public ResponseEntity<?> acceptFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendshipService.acceptFriendRequest(userId, friendId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Solicitud aceptada"));
    }
}
