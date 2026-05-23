package maurezg7.backend.repository;

import java.util.Optional;
import maurezg7.backend.models.entity.StateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StateUserRepository  extends JpaRepository<StateUser, Long>{
   @Query("SELECT s FROM StateUser s WHERE s.idUser = :data")
    StateUser getStateUser(@Param("data") Long data);
}
