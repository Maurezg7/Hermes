package maurezg7.backend.services;

import java.util.List;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.ServerDTO;
import maurezg7.backend.models.DTO.ServerRequestDTO;
import maurezg7.backend.models.entity.ServerRequest;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.ChannelRequestRepository;
import maurezg7.backend.repository.ServerRepository;
import maurezg7.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServerRequestService {
    private final ChannelRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final NotificationService notificationService;

    public ServerRequestService(ChannelRequestRepository rr, UserRepository ur, ServerRepository sr, NotificationService ns) {
        this.requestRepository = rr;
        this.userRepository = ur;
        this.serverRepository = sr;
        this.notificationService = ns;
    }

    @Transactional
    public ServerRequestDTO createRequest(Long userId, Long serverId, Long invitedByUserId) {
        if (requestRepository.existsByUserIdAndServerIdAndStatus(userId, serverId, "PENDING")) {
            throw new IllegalStateException("Ya tienes una solicitud pendiente para este servidor.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourceNotFoundException("Server no encontrado"));
        
        User invitedBy = null;
        if (invitedByUserId != null) {
            invitedBy = userRepository.findById(invitedByUserId).orElse(null);
        }

        ServerRequest request = new ServerRequest();
        request.setUser(user);
        request.setServer(server);
        
        ServerRequest savedRequest = requestRepository.save(request);
        
        String serverName = server.getName();
        String inviterName = (invitedBy != null) ? invitedBy.getUsername() : "Un administrador";
        
        notificationService.createNotification(
            userId,
            "SERVER_JOIN_REQUEST",
            inviterName + " te ha invitado a unirte a " + serverName,
            invitedByUserId,
            server.getId(),
            savedRequest.getId()
        );

        return mapToDTO(savedRequest);
    }

    @Transactional(readOnly = true)
    public List<ServerRequestDTO> getPendingRequestsByServer(Long serverId) {
        return requestRepository.findByServer_IdAndStatus(serverId, "PENDING")
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public ServerRequestDTO processRequest(Long requestId, String newStatus, Long adminId) {
        ServerRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));

        if (!request.getServer().getUser().getId().equals(adminId)) {
            throw new RuntimeException("No tienes permisos de administrador en este servidor.");
        }

        if (!newStatus.equals("APPROVED") && !newStatus.equals("REJECTED")) {
            throw new IllegalArgumentException("Estado no válido: " + newStatus);
        }

        request.setStatus(newStatus);
        
        String serverName = request.getServer().getName();
        String username = request.getUser().getUsername();
        Long serverAdminId = request.getServer().getUser().getId();
        
        if ("APPROVED".equals(newStatus)) {
            Server server = request.getServer();
            User user = request.getUser();
            server.addMember(user);
            serverRepository.save(server);
            
            notificationService.createNotification(request.getUser().getId(),
                "SERVER_JOIN_APPROVED",
                "Te han aceptado en " + serverName,
                adminId,
                request.getServer().getId(),
                request.getId());
            
            notificationService.createNotification(serverAdminId,
                "SERVER_INVITE_ACCEPTED",
                username + " se ha unido a " + serverName,
                request.getUser().getId(),
                request.getServer().getId(),
                request.getId());
        } else {
            notificationService.createNotification(request.getUser().getId(),
                "SERVER_JOIN_REJECTED",
                "Tu solicitud para unirte a " + serverName + " ha sido rechazada",
                adminId,
                request.getServer().getId(),
                request.getId());
        }
        
        requestRepository.save(request);

        return mapToDTO(request);
    }

    @Transactional
    public ServerRequestDTO userRespondToInvite(Long requestId, Long userId, String response) {
        ServerRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));

        if (!request.getUser().getId().equals(userId)) {
            throw new RuntimeException("No tienes permisos para responder esta invitación.");
        }

        request.setStatus(response);
        requestRepository.save(request);

        String serverName = request.getServer().getName();
        String username = request.getUser().getUsername();
        Long serverAdminId = request.getServer().getUser().getId();

        if ("APPROVED".equals(response)) {
            Server server = request.getServer();
            User user = request.getUser();
            server.addMember(user);
            serverRepository.save(server);
            
            notificationService.createNotification(serverAdminId,
                "SERVER_INVITE_ACCEPTED",
                username + " ha aceptado tu invitación a " + serverName,
                userId,
                request.getServer().getId(),
                request.getId());
        } else {
            notificationService.createNotification(serverAdminId,
                "SERVER_INVITE_REJECTED",
                username + " ha rechazado tu invitación a " + serverName,
                userId,
                request.getServer().getId(),
                request.getId());
        }

        return mapToDTO(request);
    }

    private ServerRequestDTO mapToDTO(ServerRequest request) {
        ServerDTO serverDTO = new ServerDTO(
                request.getServer().getId(),
                request.getServer().getName(),
                request.getServer().getDescription()
        );
        
        return new ServerRequestDTO(
                request.getId(),
                request.getUser().getId(),
                request.getUser().getUsername(),
                serverDTO,
                request.getStatus(),
                request.getCreatedAt());
    }
}
