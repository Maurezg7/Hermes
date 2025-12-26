package maudev.backend.repository;

import jakarta.transaction.Transactional;
import maudev.backend.model.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServerRepository extends JpaRepository<Server, Long> {
    @Query("SELECT s FROM Server s WHERE s.name = :name")
    Server findByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Server s WHERE s.name = :name")
    void deleteByName(@Param("name") String name);
}
