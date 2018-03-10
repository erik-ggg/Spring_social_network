package com.snetwork.repositories;

import com.snetwork.entities.model.Friends;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendsRepository extends CrudRepository<Friends, Long>{

    @Query("SELECT f FROM Friends f WHERE ID = ?1")
    Friends findOne(Long id);
    @Query("SELECT f FROM Friends f WHERE (ID_RECEIVER = ?1 OR ID_SENDER = ?1)")
    List<Friends> findByIdUser(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Friends SET ACCEPTED = TRUE WHERE (ID_SENDER = ?1 AND ID_RECEIVER = ?2)")
    void acceptFriendRequest(Long senderId, Long receiverId);
}
