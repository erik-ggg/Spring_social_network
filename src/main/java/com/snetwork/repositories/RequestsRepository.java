package com.snetwork.repositories;

import com.snetwork.entities.model.Request;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestsRepository extends CrudRepository<Request, Long>{

    @Query("SELECT f FROM Request f WHERE ID = ?1")
    Request findOne(Long id);
    @Query("SELECT f FROM Request f WHERE (ID_RECEIVER = ?1 OR ID_SENDER = ?1)")
    List<Request> findByIdUser(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Request SET ACCEPTED = TRUE WHERE (ID_SENDER = ?1 AND ID_RECEIVER = ?2)")
    void acceptFriendRequest(Long senderId, Long receiverId);
    @Query("SELECT r FROM Request r WHERE ID_RECEIVER = ?1 AND ACCEPTED = FALSE")
    List<Request> findByReceiverId(Long id);
}
