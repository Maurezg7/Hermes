package maurezg7.backend.controller;

import maurezg7.backend.models.DTO.ServerRequestDTO;
import maurezg7.backend.services.ServerRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server-requests")
@CrossOrigin(origins = "https://vercel.app")
public class ChannelRequestController {

    private final ServerRequestService requestService;

    public ChannelRequestController(ServerRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/user/{userId}/server/{serverId}")
    public ResponseEntity<ServerRequestDTO> createRequest(
            @PathVariable Long userId, 
            @PathVariable Long serverId,
            @RequestParam(required = false) Long invitedBy) {
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(requestService.createRequest(userId, serverId, invitedBy));
    }

    @GetMapping("/server/{serverId}/pending")
    public ResponseEntity<List<ServerRequestDTO>> getPendingRequests(@PathVariable Long serverId) {
        return ResponseEntity.ok(requestService.getPendingRequestsByServer(serverId));
    }

    @PatchMapping("/{requestId}/process")
    public ResponseEntity<ServerRequestDTO> processRequest(
            @PathVariable Long requestId,
            @RequestParam String status,
            @RequestParam Long adminId) {
        
        return ResponseEntity.ok(requestService.processRequest(requestId, status, adminId));
    }

    @PatchMapping("/{requestId}/respond")
    public ResponseEntity<ServerRequestDTO> userRespondToInvite(
            @PathVariable Long requestId,
            @RequestParam Long userId,
            @RequestParam String response) {
        
        return ResponseEntity.ok(requestService.userRespondToInvite(requestId, userId, response));
    }
}
