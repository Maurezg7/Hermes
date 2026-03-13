package maurezg7.backend.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "channels", 
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_channel_name_per_server", 
            columnNames = {"name", "server_id"}
        )
    },
    indexes = {
        @Index(name = "idx_server_id", columnList = "server_id")
    }
)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    
    @ManyToOne(fetch  = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;
    
    @PrePersist
    protected void onCreate(){
        this.creation = LocalDateTime.now();
    }

    public Channel() {
    }

    public Channel(Long id, String name, String description, User creator, Server server) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.server = server;
    }

    public Channel(String name, String description, User creator, Server server) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.server = server;
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

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    
    
}
