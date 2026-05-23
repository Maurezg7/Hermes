package maurezg7.backend.repository;

import maurezg7.backend.models.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long>{
    @Query("SELECT m FROM Message m WHERE " +
           "(m.emisor.id = :u1 AND m.receptor.id = :u2) OR " +
           "(m.emisor.id = :u2 AND m.receptor.id = :u1) " +
           "ORDER BY m.createdAt DESC")
    Page<Message> findDirectMessages(@Param("u1") Long u1, @Param("u2") Long u2, Pageable pageable);
}
