package maurezg7.backend.models.DTO;

import java.util.List;

public class ServerDTO {
    private Long id;
    private String name;
    private String description;
    private List<UserDTO> members;

    public ServerDTO() {
    }

    public ServerDTO(Long id, String name, String description) {
        this.id  = id;
        this.name = name;
        this.description = description;
    }

    public ServerDTO(Long id, String name, String description, List<UserDTO> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}
