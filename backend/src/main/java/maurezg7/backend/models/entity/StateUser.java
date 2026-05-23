package maurezg7.backend.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import maurezg7.backend.models.Enum.StatesUser;

@Entity
@Table(name = "state")
public class StateUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long idUser;
    
    @Enumerated(EnumType.STRING)
    private StatesUser state;

    public StateUser() {
    }

    public StateUser(Long id, Long idUser, StatesUser state) {
        this.id = id;
        this.idUser = idUser;
        this.state = state;
    }

    public StateUser(Long idUser, StatesUser state) {
        this.idUser = idUser;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public StatesUser getState() {
        return state;
    }

    public void setState(StatesUser state) {
        this.state = state;
    }
}
