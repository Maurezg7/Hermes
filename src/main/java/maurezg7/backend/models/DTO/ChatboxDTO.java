package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public class ChatboxDTO {
    private String content;
    private LocalDateTime createdAt;
    private Long creator_id;
    private String username;

    public ChatboxDTO() {
    }

    public ChatboxDTO(String content, LocalDateTime createdAt, Long creator_id, String username) {
        this.content = content;
        this.createdAt = createdAt;
        this.creator_id = creator_id;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
