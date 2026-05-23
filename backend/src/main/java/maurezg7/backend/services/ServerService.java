package maurezg7.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.ServerDTO;
import maurezg7.backend.models.DTO.UserDTO;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.ServerRepository;
import maurezg7.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServerService {
    private final ServerRepository serverRepository;
    private final UserRepository userRepository;

    public ServerService(ServerRepository serverRepository, UserRepository userRepository) {
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
    }

    public Set<String> getMembers(Long serverId) {
        return serverRepository.findUsernamesByServerId(serverId);
    }

    @Transactional(readOnly = true)
    public List<ServerDTO> getAllServers() {
        return serverRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ServerDTO> getAllUserServers(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }

        return serverRepository.findAllByUserId(userId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ServerDTO> getServer(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del servidor no puede estar vacío.");
        }
        return serverRepository.findByName(name)
                .map(this::convertToDTO);
    }

    @Transactional
    public ServerDTO createServer(Server server, Long idUser) {
        if (serverRepository.existsByName(server.getName())) {
            
            throw new IllegalStateException("Ya existe un servidor con ese nombre.");
        }

        User creator = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUser));

        server.setUser(creator);
        Server savedServer = serverRepository.save(server);
        this.addMemberToServer(savedServer.getId(), idUser);
        return convertToDTO(savedServer);
    }
    
    @Transactional
    public void addMemberToServer(Long serverId, Long userId) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Servidor no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        server.addMember(user);
        
        serverRepository.save(server);
    }

    @Transactional
    public ServerDTO updateServer(ServerDTO dto, Long idServer) {
        Server server = serverRepository.findById(idServer)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor no encontrado"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            server.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            server.setDescription(dto.getDescription());
        }

        Server updatedServer = serverRepository.save(server);
        return convertToDTO(updatedServer);
    }

    @Transactional
    public void deleteServer(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Nombre inválido.");
        }
        serverRepository.deleteById(id);
    }

    private ServerDTO convertToDTO(Server server) {
        List<UserDTO> membersDTO = server.getMembers().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return new ServerDTO(server.getId(), server.getName(), server.getDescription(), membersDTO);
    }
}
