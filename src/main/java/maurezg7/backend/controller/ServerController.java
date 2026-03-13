package maurezg7.backend.controller;

import java.util.List;
import maurezg7.backend.models.DTO.ServerDTO;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/servers")
public class ServerController {
    @Autowired
    private ServerService serverService;
    
    
    @GetMapping
    public ResponseEntity<List<ServerDTO>> getAllServers(){
        return ResponseEntity.ok(this.serverService.getAllServers());
    }
    
    @GetMapping("/{name}")
    public ResponseEntity<ServerDTO> getServer(@PathVariable String name){
        return serverService.getServer(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/user/{idUser}")
    public ResponseEntity<ServerDTO> createServer(@RequestBody Server server, @PathVariable Long idUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serverService.createServer(server, idUser));
    }

    @PutMapping("/{idServer}")
    public ResponseEntity<ServerDTO> updateServer(@RequestBody ServerDTO serverReceived, @PathVariable Long idServer){
        return ResponseEntity.ok(serverService.updateServer(serverReceived, idServer));
    }
    
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteServer(@PathVariable String name){
        serverService.deleteServer(name);
        return ResponseEntity.noContent().build();
    }
}
