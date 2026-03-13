package maurezg7.backend.services;

import java.util.List;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.MessageDTO;
import maurezg7.backend.models.entity.Message;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.MessageRepository;
import maurezg7.backend.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    
    public MessageService(MessageRepository messageRepository, UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
    public List<MessageDTO> getRecentMessages(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        return messageRepository.findAll(pageable).stream()
                .map(m -> new MessageDTO(m.getId(), m.getContent(), m.getCreatedAt(), m.getEmisor().getId(), m.getEmisor().getUsername(), m.getReceptor().getId(), m.getReceptor().getUsername()))
                .toList();
    }
    
    @Transactional
    public MessageDTO createMessage(Message message, Long idEmisor, Long idReceptor){
        User emisor  = this.userRepository.findById(idEmisor).orElseThrow(() -> new ResourceNotFoundException("Usuario emisor no encontrado."));
        User receptor = this.userRepository.findById(idReceptor).orElseThrow(() -> new ResourceNotFoundException("Usuario receptor no encontrado."));
        
        message.setEmisor(emisor);
        message.setReceptor(receptor);
        
        Message messageSaved = this.messageRepository.save(message);
        
        return new MessageDTO(messageSaved.getId(),messageSaved.getContent(), messageSaved.getCreatedAt(), messageSaved.getEmisor().getId(), messageSaved.getEmisor().getUsername(), messageSaved.getReceptor().getId(), messageSaved.getReceptor().getUsername());
    }
    
    @Transactional
    public MessageDTO updateMessage(MessageDTO messageReceived, Long idMessage, Long idUserSolicitante) {
        Message message = this.messageRepository.findById(idMessage)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado."));

        if (!message.getEmisor().getId().equals(idUserSolicitante)) {
            throw new RuntimeException("No tienes permiso para editar este mensaje.");
        }

        if (messageReceived.getContent() != null) {
            message.setContent(messageReceived.getContent());
        }

        this.messageRepository.save(message);

        return new MessageDTO(
            message.getId(), 
            message.getContent(), 
            message.getCreatedAt(), 
            message.getEmisor().getId(), 
            message.getEmisor().getUsername(), 
            message.getReceptor().getId(), 
            message.getReceptor().getUsername()
        );
    }
}
