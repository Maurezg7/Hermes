package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public record NotificationDTO(
    Long id,
    Long userId,
    String username,
    String type,
    String message,
    boolean isRead,
    Long relatedUserId,
    String relatedUsername,
    Long relatedServerId,
    Long relatedChannelId,
    Long relatedFriendshipId,
    LocalDateTime createdAt
) {}
