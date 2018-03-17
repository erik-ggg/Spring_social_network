package com.snetwork.repositories;

import com.snetwork.entities.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestsRepository extends CrudRepository<Request, Long>{

    @Query("SELECT f FROM Request f WHERE ID = ?1")
    Request findOne(Long id);
    @Query("SELECT f FROM Request f WHERE (RECEIVER_ID = ?1 OR SENDER_ID = ?1)")
    List<Request> findByUserId(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Request SET ACCEPTED = TRUE WHERE (SENDER_ID = ?1 AND RECEIVER_ID = ?2)")
    void acceptFriendRequest(Long senderId, Long receiverId);
    @Query("SELECT r FROM Request r WHERE RECEIVER_ID = ?1 AND ACCEPTED = FALSE")
    Page<Request> findByReceiverId(Pageable pageable, Long id);
    @Query("SELECT r FROM Request r WHERE (RECEIVER_ID = ?1 OR SENDER_ID = ?1) AND ACCEPTED = TRUE")
    Page<Request> findFriends(Pageable pageable, Long id);
}
