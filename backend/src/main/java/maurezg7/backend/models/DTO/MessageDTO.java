package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long emisorId;
    private String emisorUsername;
    private Long receptorId;
    private String receptorUsername;

    public MessageDTO() {}

    public MessageDTO(Long id, String content, LocalDateTime createdAt, 
                      Long emisorId, String emisorUsername, 
                      Long receptorId, String receptorUsername) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.emisorId = emisorId;
        this.emisorUsername = emisorUsername;
        this.receptorId = receptorId;
        this.receptorUsername = receptorUsername;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getEmisorId() { return emisorId; }
    public String getEmisorUsername() { return emisorUsername; }
    public Long getReceptorId() { return receptorId; }
    public String getReceptorUsername() { return receptorUsername; }

    public void setId(Long id) { this.id = id; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }
    public void setEmisorUsername(String emisorUsername) { this.emisorUsername = emisorUsername; }
    public void setReceptorId(Long receptorId) { this.receptorId = receptorId; }
    public void setReceptorUsername(String receptorUsername) { this.receptorUsername = receptorUsername; }
}