package maudev.backend.controller;

import maudev.backend.model.entity.Server;
import maudev.backend.service.ServerService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hermes-app")
@CrossOrigin(value = "http://localhost/4200")
public class ServerController {
    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    @Autowired
    private ServerService serverService;

    @GetMapping("/server")
    public List<Server> getAllServers() {
        List<Server> servers = this.serverService.findAll();
        if(servers.isEmpty()){
            throw new RuntimeException("Server List is Empty");
        }
        servers.forEach(server -> {
            logger.info(server.toString());
        });
        return servers;
    }

    @PostMapping("/server")
    public Server createServer(@RequestBody Server server){
        if(server == null){
            throw new RuntimeException("Server object is null");
        }
        logger.info("Server created");
        return this.serverService.saveServer(server);
    }

    @GetMapping("/server/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable("id") Long id){
        Server server = this.serverService.findById(id);
        if(server == null){
            throw new RuntimeException("Server Not Found");
        }
        return ResponseEntity.ok(server);
    }

    @PutMapping("/server/{id}")
    public ResponseEntity<Server> updateServer(@PathVariable("id") Long id, @RequestBody Server serverReceived){
        Server server = this.serverService.findById(id);
        if(server == null){
            throw new RuntimeException("User Not Found");
        }
        server.setName(serverReceived.getName());
        server.setDescription(serverReceived.getDescription());
        server.setId_user(serverReceived.getId_user());
        this.serverService.saveServer(server);
        return ResponseEntity.ok(server);
    }

    @DeleteMapping("/server/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteServer(@PathVariable("id") Long id){
        Server server = this.serverService.findById(id);
        if (server == null){
            throw new RuntimeException("Server Not Found");
        }
        this.serverService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
