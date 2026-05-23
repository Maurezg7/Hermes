package maurezg7.backend.models.DTO;

public class ChannelDTO {
    private String name;
    private String description;
    private Long channelId;
    
    public ChannelDTO(){}
    
    public ChannelDTO(Long channelId, String name, String description){
        this.channelId = channelId;
        this.name = name;
        this.description = description;
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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
