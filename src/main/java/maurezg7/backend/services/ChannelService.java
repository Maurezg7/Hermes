package maurezg7.backend.services;

import java.util.List;
import java.util.Optional;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.ChannelDTO;
import maurezg7.backend.models.entity.Channel;
import maurezg7.backend.models.entity.Server;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.ChannelRepository;
import maurezg7.backend.repository.ServerRepository;
import maurezg7.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final UserRepository userRepository;
    
    public ChannelService(ChannelRepository channelRepository, ServerRepository serverRepository, UserRepository userRepository){
        this.channelRepository = channelRepository;
        this.serverRepository = serverRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
    public List<ChannelDTO> getAllChannels(){
        List<Channel> channels = this.channelRepository.findAll();
        
        return channels.stream()
                .map(c -> new ChannelDTO(c.getName(), c.getDescription(), c.getCreation(), c.getServer().getId(), c.getCreator().getUsername()))
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<ChannelDTO> getChannel(String name, Server server){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("El nombre del canal no puede estar vacio");
        }
        
        return channelRepository.findByNameAndServer(name, server)
                .map(c -> new ChannelDTO(c.getName(), c.getDescription(), c.getCreation(), c.getServer().getId(), c.getCreator().getUsername()));
    }
    
    @Transactional
    public ChannelDTO createChannel(Channel channel, Long idServer, Long idUser){
        User creator = this.userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUser));
        
        Server serverCreator = this.serverRepository.findById(idServer)
                .orElseThrow(() -> new ResourceNotFoundException("Server no encontrado con ID: " + idServer));
        
        channel.setCreator(creator);
        channel.setServer(serverCreator);
        
        Channel savedChannel = this.channelRepository.save(channel);
        
        return new ChannelDTO(savedChannel.getName(), savedChannel.getDescription(), savedChannel.getCreation(),savedChannel.getServer().getId(), savedChannel.getCreator().getUsername());
    }
    
    @Transactional
    public ChannelDTO updateChannel(ChannelDTO channelReceived, Long idChannel){
        Channel channel = channelRepository.findById(idChannel)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado con ID; " + idChannel));
        
        if(channelReceived.getName() != null) channel.setName(channelReceived.getName());
        if(channelReceived.getDescription() != null) channel.setDescription(channelReceived.getDescription());
        
        this.channelRepository.save(channel);
        
        return new ChannelDTO(channel.getName(), channel.getDescription(), channel.getCreation(),channel.getServer().getId(), channel.getCreator().getUsername());
    }
    
    @Transactional
    public void deleteChannel(String name, Long idServer){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Nombre invalido");
        }
        
        if(idServer == 0){
            throw new IllegalArgumentException("Id del server invalido: " + idServer);
        }
        
        int deleted = this.channelRepository.deleteByNameAndServerId(name, idServer);
        
        if(deleted == 0){
            throw new ResourceNotFoundException("No se encontró el canal para eliminar.");
        }
    }
}
