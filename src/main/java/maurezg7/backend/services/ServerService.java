package maurezg7.backend.services;

import java.util.List;
import java.util.Optional;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.ServerDTO;
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

    @Transactional(readOnly = true)
    public List<ServerDTO> getAllServers() {
        List<Server> servers = serverRepository.findAll();
        
        return servers.stream()
                .map(s -> new ServerDTO(s.getName(), s.getDescription()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ServerDTO> getServer(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del servidor no puede estar vacío.");
        }

        return serverRepository.findByName(name)
                .map(s -> new ServerDTO(s.getName(), s.getDescription()));
    }

    @Transactional
    public ServerDTO createServer(Server server, Long idUser) {
        User creator = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUser));

        server.setUser(creator);
        Server savedServer = serverRepository.save(server);
        
        return new ServerDTO(savedServer.getName(), savedServer.getDescription());
    }

    @Transactional
    public ServerDTO updateServer(ServerDTO serverReceived, Long idServer) {
        Server server = serverRepository.findById(idServer)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor no encontrado"));

        if (serverReceived.getName() != null) server.setName(serverReceived.getName());
        if (serverReceived.getDescrription() != null) server.setDescription(serverReceived.getDescrription());

        return new ServerDTO(server.getName(), server.getDescription());
    }

    @Transactional
    public void deleteServer(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido.");
        }
        
        int deleted = serverRepository.deleteByName(name);
        if (deleted == 0) {
            throw new ResourceNotFoundException("No se encontró el servidor para eliminar.");
        }
    }
}
