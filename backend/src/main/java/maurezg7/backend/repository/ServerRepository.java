package maurezg7.backend.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import maurezg7.backend.models.entity.Server;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByName(String name);

    @Query("SELECT DISTINCT s FROM Server s LEFT JOIN s.members m WHERE s.user.id = :id OR m.id = :id")
    List<Server> findAllByUserId(@Param("id") Long id); 
    
    @Query("SELECT m.username FROM Server s JOIN s.members m WHERE s.id = :serverId")
    Set<String> findUsernamesByServerId(@Param("serverId") Long serverId);

    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Server s WHERE s.name = :name")
    int deleteByName(@Param("name") String name);
}
