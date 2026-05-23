package maurezg7.backend.models.DTO;

public class ChangePassword {
    private String datauser;
    private String oldpassword;
    private String newpassword;

    public ChangePassword(String datauser, String oldpassword, String newpassword) {
        this.datauser = datauser;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    public String getDatauser() {
        return datauser;
    }

    public void setDatauser(String datauser) {
        this.datauser = datauser;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
