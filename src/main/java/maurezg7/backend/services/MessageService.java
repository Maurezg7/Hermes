package maurezg7.backend.services;

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
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getDirectMessages(Long u1Id, Long u2Id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        return messageRepository.findDirectMessages(u1Id, u2Id, pageable)
                .getContent() 
                .stream()
                .map(this::convertToDTO)
                .toList();
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

        return convertToDTO(messageRepository.save(message));
    }

    @Transactional
    public MessageDTO createMessage(Message message, Long idEmisor, Long idReceptor) {
        User emisor = userRepository.findById(idEmisor)
                .orElseThrow(() -> new ResourceNotFoundException("Emisor no encontrado"));
        User receptor = userRepository.findById(idReceptor)
                .orElseThrow(() -> new ResourceNotFoundException("Receptor no encontrado"));

        message.setEmisor(emisor);
        message.setReceptor(receptor);

        return convertToDTO(messageRepository.save(message));
    }

    @Transactional
    public void deleteMessage(Long idMessage, Long idUserSolicitante) {
        Message message = messageRepository.findById(idMessage)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado"));

        if (!message.getEmisor().getId().equals(idUserSolicitante)) {
            throw new RuntimeException("Sin permisos para eliminar");
        }
        messageRepository.delete(message);
    }

    private MessageDTO convertToDTO(Message m) {
        return new MessageDTO(
            m.getId(), 
            m.getContent(), 
            m.getCreatedAt(), 
            m.getEmisor().getId(), 
            m.getEmisor().getUsername(), 
            m.getReceptor().getId(), 
            m.getReceptor().getUsername()
        );
    }
}