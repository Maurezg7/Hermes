package maurezg7.backend.controller;

import java.util.List;
import maurezg7.backend.models.DTO.ChatboxDTO;
import maurezg7.backend.models.entity.Chatbox;
import maurezg7.backend.services.ChatboxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/chatbox")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatboxController {
    private final ChatboxService chatboxService;
    
    public ChatboxController(ChatboxService chatboxService){
        this.chatboxService = chatboxService;
    }
    
    @GetMapping("/channel/{id}/paged")
    public ResponseEntity<List<ChatboxDTO>> getPagedMessages(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        return ResponseEntity.ok(chatboxService.getRecentMessages(id, page, size));
    }
    
    @PostMapping("/channel/{idChannel}/user/{idUser}")
    public ResponseEntity<ChatboxDTO> createChatbox(@RequestBody Chatbox chatbox, @PathVariable Long idChannel, @PathVariable Long idUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chatboxService.createChannel(chatbox, idChannel, idUser));
    }
}
