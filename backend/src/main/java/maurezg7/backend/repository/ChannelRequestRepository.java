package maurezg7.backend.repository;

import java.util.List;
import maurezg7.backend.models.entity.ServerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRequestRepository extends JpaRepository<ServerRequest, Long> {
    @Query("SELECT COUNT(cr) > 0 FROM ServerRequest cr WHERE cr.user.id = :userId AND cr.server.id = :serverId AND cr.status = :status")
    boolean existsByUserIdAndServerIdAndStatus(@Param("userId") Long userId, @Param("serverId") Long serverId, @Param("status") String status);

    List<ServerRequest> findByServer_IdAndStatus(Long serverId, String status);
}
