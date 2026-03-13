package maurezg7.backend.models.DTO;

public class ServerDTO {
    private String name;
    private String descrription;

    public ServerDTO() {
    }

    public ServerDTO(String name, String descrription) {
        this.name = name;
        this.descrription = descrription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrription() {
        return descrription;
    }

    public void setDescrription(String descrription) {
        this.descrription = descrription;
    }
    
}
