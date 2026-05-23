package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public class ServerRequestDTO {
    private Long requestId;
    private Long userId;
    private String username;
    private ServerDTO server;
    private String status;
    private LocalDateTime requestedAt;

    public ServerRequestDTO() {
    }

    public ServerRequestDTO(Long requestId, Long userId, String username, ServerDTO server, String status, LocalDateTime requestedAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.username = username;
        this.server = server;
        this.status = status;
        this.requestedAt = requestedAt;
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

    public ServerDTO getServer() {
        return server;
    }

    public void setServer(ServerDTO server) {
        this.server = server;
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
