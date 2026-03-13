package maurezg7.backend.models.DTO;

import java.time.LocalDateTime;

public class ChannelDTO {
    private String name;
    private String description;
    private LocalDateTime creation;
    private Long serverId;
    private Long channelId;
    private String creatorName;
    
    public ChannelDTO(){}
    
    public ChannelDTO(String name, String description, LocalDateTime creation, Long serverId, String creatorName){
        this.name = name;
        this.description = description;
        this.creation = creation;
        this.serverId = serverId;
        this.creatorName = creatorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
