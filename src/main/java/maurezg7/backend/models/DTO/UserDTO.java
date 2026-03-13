package maurezg7.backend.models.DTO;

public class UserDTO {
    private String username;
    private String email;
    
    public UserDTO(String username, String email){
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
