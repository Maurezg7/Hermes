package maudev.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import maudev.backend.model.entity.Chats;
import maudev.backend.service.ChatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hermes-app")
@CrossOrigin(value = "http://localhost/4200")
public class ChatsController {
    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
    
    @Autowired
    private ChatsService chatService;
    
    @GetMapping("/chats")
    public List<Chats> getChats(){
        List<Chats> chats = this.chatService.getAllChats();
        if(chats.isEmpty()){
            throw new RuntimeException("Chats list is empty.");
        }
        chats.forEach(chat -> {
            logger.info(chat.toString());
        });
        return chats;
    }
    
    @PostMapping("/chats")
    public Chats createChat(@RequestBody Chats chat){
        if(chat == null){
            throw new RuntimeException("Chat object is null");
        }
        logger.info("Chat created.");
        return this.chatService.saveChat(chat);
    }
    
    @GetMapping("/chats/{id}")
    public ResponseEntity<Chats> getChatById(@PathVariable("id") Long id){
        Chats chat = this.chatService.getChatById(id);
        if(chat == null){
             throw new RuntimeException("Chat not found");
        }
        return ResponseEntity.ok(chat);
    }
    
    @PutMapping("/chats/{id}")
    public ResponseEntity<Chats> updateChat(@PathVariable("id") Long id, @RequestBody Chats chatReceived){
        Chats chat = this.chatService.getChatById(id);
        if(chat == null){
            throw new RuntimeException("Chat not found");
        }
        chat.setMessage(chatReceived.getMessage());
        this.chatService.saveChat(chat);
        return ResponseEntity.ok(chat);
    }
    
    @DeleteMapping("/chats/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteChat(@PathVariable("id") Long id){
        Chats chat = this.chatService.getChatById(id);
        if(chat == null){
             throw new RuntimeException("Chat not found");
        }
        this.chatService.deleteChat(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
