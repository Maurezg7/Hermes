package maurezg7.backend.services;

import java.util.List;
import maurezg7.backend.models.Enum.FriendshipStatus;
import maurezg7.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import maurezg7.backend.models.DTO.*;
import maurezg7.backend.models.entity.*;
import maurezg7.backend.repository.*;
import org.springframework.data.domain.*;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    public FriendshipService(FriendshipRepository friendshipRepository, 
                             UserRepository userRepository,
                             MessageRepository messageRepository,
                             NotificationService notificationService) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void sendFriendRequest(Long userId, Long friendId) {
        if (userId.equals(friendId)) throw new IllegalArgumentException("No puedes agregarte a ti mismo");

        friendshipRepository.findRelation(userId, friendId).ifPresent(f -> {
            throw new IllegalStateException("Ya existe una relación o solicitud pendiente");
        });

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new ResourceNotFoundException("Amigo no encontrado"));

        Friendship friendship = new Friendship(user, friend, FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);

        notificationService.createNotification(friendId,
            "FRIEND_REQUEST",
            user.getUsername() + " te ha enviado una solicitud de amistad",
            userId,
            null,
            null);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getPrivateMessages(Long userId, Long friendId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        return messageRepository.findDirectMessages(userId, friendId, pageable)
                .getContent()
                .stream()
                .map(m -> new MessageDTO(
                    m.getId(), 
                    m.getContent(), 
                    m.getCreatedAt(), 
                    m.getEmisor().getId(),
                    m.getEmisor().getUsername(),
                    m.getReceptor().getId(),
                    m.getReceptor().getUsername()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FriendDTO> getPendingRequests(Long userId) {
        return friendshipRepository.findAll().stream()
                .filter(f -> f.getFriend().getId().equals(userId) && f.getStatus() == FriendshipStatus.PENDING)
                .map(f -> new FriendDTO(f.getUser().getId(), f.getUser().getUsername(), f.getUser().getEmail(), f.getStatus()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FriendDTO> getAcceptedFriends(Long userId) {
        return friendshipRepository.findAllAcceptedFriends(userId).stream()
                .map(f -> {
                    User friend = f.getUser().getId().equals(userId) ? f.getFriend() : f.getUser();
                    return new FriendDTO(friend.getId(), friend.getUsername(), friend.getEmail(), f.getStatus());
                }).toList();
    }
    
    @Transactional
    public void blockUser(Long userId, Long targetId) {
        Friendship f = friendshipRepository.findRelation(userId, targetId)
                .orElseGet(() -> {
                    User u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
                    User t = userRepository.findById(targetId).orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado"));
                    return new Friendship(u, t, FriendshipStatus.PENDING);
                });
        
        f.setStatus(FriendshipStatus.BLOCKED);
        friendshipRepository.save(f);
    }
    
    @Transactional
    public void acceptFriendRequest(Long userId, Long friendId) {
        Friendship f = friendshipRepository.findRelation(userId, friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));
        
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        f.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(f);

        notificationService.createNotification(friendId,
            "FRIEND_ACCEPTED",
            user.getUsername() + " ha aceptado tu solicitud de amistad",
            userId,
            null,
            null);
    }
}