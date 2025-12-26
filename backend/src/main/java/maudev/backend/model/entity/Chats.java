package maudev.backend.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "chats")
public class Chats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String channel_id;
    private String user_id;

    public Chats(Long id, String message, String channel_id, String user_id) {
        this.id = id;
        this.message = message;
        this.channel_id = channel_id;
        this.user_id = user_id;
    }

    public Chats(String message, String channel_id, String user_id) {
        this.message = message;
        this.channel_id = channel_id;
        this.user_id = user_id;
    }

    public Chats() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Chats{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chats chats = (Chats) o;
        return Objects.equals(id, chats.id) && Objects.equals(message, chats.message) && Objects.equals(channel_id, chats.channel_id) && Objects.equals(user_id, chats.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, channel_id, user_id);
    }
}
