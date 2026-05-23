package maurezg7.backend.controller;

import java.util.List;
import java.util.Set;
import maurezg7.backend.models.DTO.ServerDTO;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.services.ServerService;
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
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/servers")
@CrossOrigin(origins = "https://vercel.app")
public class ServerController {
    private final ServerService serverService;
    
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public ResponseEntity<List<ServerDTO>> getAllServers() {
        return ResponseEntity.ok(serverService.getAllServers());
    }
    
    @GetMapping("/user/members/{idServer}")
    public ResponseEntity<Set<String>> getMembersServer(@PathVariable Long idServer){
        return ResponseEntity.ok(serverService.getMembers(idServer));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ServerDTO>> getAllUserServers(@PathVariable Long id) {
        return ResponseEntity.ok(serverService.getAllUserServers(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ServerDTO> getServer(@PathVariable String name) {
        return serverService.getServer(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{idUser}")
    public ResponseEntity<ServerDTO> createServer(@RequestBody Server server, @PathVariable Long idUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serverService.createServer(server, idUser));
    }

    @PutMapping("/{idServer}")
    public ResponseEntity<ServerDTO> updateServer(@RequestBody ServerDTO serverReceived, @PathVariable Long idServer) {
        return ResponseEntity.ok(serverService.updateServer(serverReceived, idServer));
    }

    @DeleteMapping("/{idserver}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long idserver) {
        this.serverService.deleteServer(idserver);
        return ResponseEntity.noContent().build();
    }
    
}
