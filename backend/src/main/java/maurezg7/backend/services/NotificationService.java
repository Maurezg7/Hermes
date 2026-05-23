package maurezg7.backend.services;

import java.util.List;
import maurezg7.backend.exception.ResourceNotFoundException;
import maurezg7.backend.models.DTO.NotificationDTO;
import maurezg7.backend.models.entity.Notification;
import maurezg7.backend.models.entity.User;
import maurezg7.backend.repository.NotificationRepository;
import maurezg7.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createNotification(Long userId, String type, String message, Long relatedUserId, Long relatedServerId, Long relatedChannelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        Notification notification = new Notification(user, type, message);
        
        if (relatedUserId != null) {
            User relatedUser = userRepository.findById(relatedUserId).orElse(null);
            notification.setRelatedUser(relatedUser);
        }
        
        notification.setRelatedServerId(relatedServerId);
        notification.setRelatedChannelId(relatedChannelId);
        
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findUnreadByUserId(userId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findUnreadByUserId(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    private NotificationDTO mapToDTO(Notification n) {
        return new NotificationDTO(
            n.getId(),
            n.getUser().getId(),
            n.getUser().getUsername(),
            n.getType(),
            n.getMessage(),
            n.isRead(),
            n.getRelatedUser() != null ? n.getRelatedUser().getId() : null,
            n.getRelatedUser() != null ? n.getRelatedUser().getUsername() : null,
            n.getRelatedServerId(),
            n.getRelatedChannelId(),
            n.getRelatedFriendshipId(),
            n.getCreatedAt()
        );
    }
}
