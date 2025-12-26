package maudev.backend.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long id_user;

    public Server(Long id, String name, String description, Long id_user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.id_user = id_user;
    }

    public Server(String name, String description, Long id_user) {
        this.name = name;
        this.description = description;
        this.id_user = id_user;
    }

    public Server() {}

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

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id_user=" + id_user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(id, server.id) && Objects.equals(name, server.name) && Objects.equals(description, server.description) && Objects.equals(id_user, server.id_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, id_user);
    }
}
