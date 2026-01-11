package maurezg7.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import maurezg7.backend.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.username = :userdata OR u.email = :userdata")
    User userLoging(@Param("userdata") String userdata);
}
