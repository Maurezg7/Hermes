package maurezg7.backend.controller;

import java.util.List;
import maurezg7.backend.models.DTO.ChannelDTO;
import maurezg7.backend.models.entity.Channel;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.services.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;
    
    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }
    
    @GetMapping
    public ResponseEntity<List<ChannelDTO>> getAllChannels(){
        return ResponseEntity.ok(this.channelService.getAllChannels());
    }
    
    @GetMapping("/server/{idServer}/{name}")
    public ResponseEntity<ChannelDTO> getChannel(@PathVariable Long idServer,@PathVariable String name){
        Server server = new Server();
        server.setId(idServer);
        
        return channelService.getChannel(name, server)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/server/{idServer}/user/{idUser}")
    public ResponseEntity<ChannelDTO> createChannel(@RequestBody Channel channel, @PathVariable Long idServer, @PathVariable Long idUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(channelService.createChannel(channel, idServer, idUser));
    }
    
    @PutMapping("/{idChannel}")
    public ResponseEntity<ChannelDTO> updateChannel(@RequestBody ChannelDTO channelReceived, @PathVariable Long idChannel){
        return ResponseEntity.ok(channelService.updateChannel(channelReceived, idChannel));
    }
    
    @DeleteMapping("/{name}/{idServer}")
    public ResponseEntity<Void> deleteChannel(@PathVariable String name, @PathVariable Long idServer){
        channelService.deleteChannel(name, idServer);
        return ResponseEntity.noContent().build();
    }
}
