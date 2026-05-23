package maurezg7.backend.models.DTO;

import maurezg7.backend.models.Enum.FriendshipStatus;

public record FriendDTO(
    Long id, 
    String username, 
    String email, 
    FriendshipStatus status
) {}