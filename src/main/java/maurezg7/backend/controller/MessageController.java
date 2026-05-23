package maurezg7.backend.controller;

import java.util.List;
import maurezg7.backend.models.DTO.MessageDTO;
import maurezg7.backend.models.entity.Message;
import maurezg7.backend.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "https://vercel.app")
public class MessageController {
    private final MessageService messageService;
    
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }
    
    @PostMapping("/emisor/{idEmisor}/receptor/{idReceptor}")
    public ResponseEntity<MessageDTO> createMessage(
            @RequestBody Message message,
            @PathVariable Long idEmisor,
            @PathVariable Long idReceptor) {
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(messageService.createMessage(message, idEmisor, idReceptor));
    }

    @PutMapping("/{idMessage}/user/{idUserSolicitante}")
    public ResponseEntity<MessageDTO> updateMessage(
            @RequestBody MessageDTO messageDTO,
            @PathVariable Long idMessage,
            @PathVariable Long idUserSolicitante) {
        return ResponseEntity.ok(messageService.updateMessage(messageDTO, idMessage, idUserSolicitante));
    }

    @DeleteMapping("/{idMessage}/user/{idUserSolicitante}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long idMessage, @PathVariable Long idUserSolicitante) {
        messageService.deleteMessage(idMessage, idUserSolicitante);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/history/{u1}/{u2}")
    public ResponseEntity<List<MessageDTO>> getChatHistory(
            @PathVariable Long u1, 
            @PathVariable Long u2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(messageService.getDirectMessages(u1, u2, page, size));
    }
}
