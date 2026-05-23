package maurezg7.backend.repository;

import maurezg7.backend.models.entity.Chatbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatboxRepository extends JpaRepository<Chatbox, Long>{
    @Query("SELECT c FROM Chatbox c JOIN FETCH c.creator WHERE c.channel.id = :idChannel")
    Page<Chatbox> findByChannelId(@Param("idChannel") Long idChannel, Pageable pageable);
}
