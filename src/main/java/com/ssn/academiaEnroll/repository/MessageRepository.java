package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.sender = :userId OR m.receiver = :userId")
    List<Message> findBySenderIdOrReceiverId(@Param("userId") Long userId);
}
