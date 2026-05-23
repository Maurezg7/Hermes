package maurezg7.backend.repository;

import java.util.List;
import java.util.Optional;
import maurezg7.backend.models.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("SELECT f FROM Friendship f WHERE (f.user.id = :id OR f.friend.id = :id) AND f.status = 'ACCEPTED'")
    List<Friendship> findAllAcceptedFriends(@Param("id") Long id);
    
    @Query("SELECT f FROM Friendship f WHERE " +
           "(f.user.id = :u1 AND f.friend.id = :u2) OR " +
           "(f.user.id = :u2 AND f.friend.id = :u1)")
    Optional<Friendship> findRelation(@Param("u1") Long u1, @Param("u2") Long u2);
}
