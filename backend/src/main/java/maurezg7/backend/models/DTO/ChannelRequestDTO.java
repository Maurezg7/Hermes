package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public class ChannelRequestDTO {
    private Long requestId;
    private Long userId;
    private String username;
    private ChannelDTO channel;
    private String status;
    private LocalDateTime requestedAt;

    public ChannelRequestDTO(Long requestId, Long userId, String username, ChannelDTO channel, String status, LocalDateTime requestedAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.username = username;
        this.channel = channel;
        this.status = status;
        this.requestedAt = requestedAt;
    }

    public ChannelRequestDTO() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
    
    
}
