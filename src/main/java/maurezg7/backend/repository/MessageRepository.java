package maurezg7.backend.repository;

import jakarta.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import maurezg7.backend.models.entity.Message;
import maurezg7.backend.models.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long>{
    @Query("SELECT m FROM Message m WHERE " +
           "(m.emisor = :u1 AND m.receptor = :u2) OR " +
           "(m.emisor = :u2 AND m.receptor = :u1) " +
           "ORDER BY m.createdAt DESC")
    List<Message> findDirectMessages(@Param("u1") User u1, @Param("u2") User u2, Pageable pageable);
    
    @Transactional
    void deleteById(Long id);
}
