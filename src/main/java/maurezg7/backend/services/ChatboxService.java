package maurezg7.backend.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import maurezg7.backend.models.DTO.ChatboxDTO;
import maurezg7.backend.models.entity.Chatbox;
import maurezg7.backend.models.entity.Channel;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.ChatboxRepository;
import maurezg7.backend.repository.ChannelRepository;
import maurezg7.backend.repository.UserRepository;
import maurezg7.backend.exception.ResourceNotFoundException;

@Service
public class ChatboxService {
    private final ChatboxRepository chatboxRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    
    public ChatboxService(ChatboxRepository chatboxRepository, ChannelRepository channelRepository, UserRepository userRepository){
        this.chatboxRepository = chatboxRepository;
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
    public List<ChatboxDTO> getRecentMessages(Long idChannel, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Chatbox> messagePage = chatboxRepository.findByChannelId(idChannel, pageable);
        
        return messagePage.stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    @Transactional
    public ChatboxDTO createChannel(Chatbox chatbox, Long idChannel, Long idUser) {
        Channel channel = this.channelRepository.findById(idChannel)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado"));

        User user = this.userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        chatbox.setChannel(channel);
        chatbox.setCreator(user);

        Chatbox savedChatbox = this.chatboxRepository.save(chatbox);
        return convertToDTO(savedChatbox);
    }
    
    private ChatboxDTO convertToDTO(Chatbox chatbox) {
        return new ChatboxDTO(
            chatbox.getContent(), 
            chatbox.getCreatedAt(), 
            chatbox.getCreator().getId(),
            chatbox.getCreator().getUsername()
        );
    }
}