package maurezg7.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "date_creation", updatable = false, columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "updated_at", updatable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    private String verificationCode;
    private LocalDateTime codeExpiration;
    
    @Column(updatable = true)
    private Boolean isVerified;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
        this.isVerified = false;
        if (this.role == null) {
            this.role = "USER";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User(){}

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getCodeExpiration() {
        return codeExpiration;
    }

    public void setCodeExpiration(LocalDateTime codeExpiration) {
        this.codeExpiration = codeExpiration;
    }

    public Boolean getUser_verify() {
        return isVerified;
    }

    public void setUser_verify(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}