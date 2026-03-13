package maurezg7.backend.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import maurezg7.backend.models.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.username = :data OR u.email = :data")
    Optional<User> findByUsernameOrEmail(@Param("data") String data);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :data OR u.email = :data")
    int removeByUserData(@Param("data") String data);
}
