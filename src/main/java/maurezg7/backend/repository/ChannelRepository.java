package maurezg7.backend.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import maurezg7.backend.models.entity.Channel;
import maurezg7.backend.models.entity.Server;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ChannelRepository extends JpaRepository<Channel, Long>{
    Optional<Channel> findByNameAndServer(String name, Server server);
    
    boolean existsByNameAndServer(String name, Server server);
    
    List<Channel> findByServer(Server server);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Channel c WHERE c.name = :name AND c.server.id = :serverId")
    int deleteByNameAndServerId(@Param("name") String name, @Param("serverId") Long serverId);
    
    @Transactional
    void deleteById(Long id);
}
