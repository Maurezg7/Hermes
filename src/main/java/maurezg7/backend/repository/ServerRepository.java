package maurezg7.backend.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import maurezg7.backend.models.entity.Server;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ServerRepository extends JpaRepository<Server, Long>{
    Optional<Server> findByName(String name);
    
    boolean existsByName(String name);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Server s WHERE s.name = :name")
    int deleteByName(@Param("name") String name);
    
    @Transactional
    void deleteById(Long id);
}
