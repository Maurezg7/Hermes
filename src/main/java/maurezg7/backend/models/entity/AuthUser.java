package maurezg7.backend.models.entity;


public class AuthUser {
    private String dataUser;
    private String dataPassword;

    public AuthUser() {}

    public AuthUser(String dataUser, String dataPassword) {
        this.dataUser = dataUser;
        this.dataPassword = dataPassword;
    }

    public String getDataUser() {
        return dataUser;
    }

    public void setDataUser(String dataUser) {
        this.dataUser = dataUser;
    }

    public String getDataPassword() {
        return dataPassword;
    }

    public void setDataPassword(String dataPassword) {
        this.dataPassword = dataPassword;
    }
}
