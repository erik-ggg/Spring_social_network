package com.snetwork.repositories;

import com.snetwork.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UsersRepository extends CrudRepository<User,Long>{
    User findByName(String name);
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE ID <> ?1")
    Page<User> findAllMinusTarget(Pageable pageable, Long id);
    @Query("SELECT u FROM User u,Request r WHERE (RECEIVER_ID = ?1 OR SENDER_ID = ?1) AND ACCEPTED = TRUE" +
            " AND (RECEIVER_ID = u.id OR SENDER_ID = u.id) AND u.id <> ?1")
    Page<User> findFriends(Pageable pageable, Long id);
    @Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE CONCAT('%',LOWER(?1) ,'%') OR LOWER(u.email) LIKE CONCAT('%',LOWER(?1) ,'%')) AND " +
            "(u.id <> ?2)")
    Page<User> searchUser(Pageable pageable, String searchText, Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Publication WHERE USER_ID = ?1")
    void deletePublications(Long id);
    @Transactional
    @Modifying
    @Query("DELETE FROM Request WHERE RECEIVER_ID = ?1 OR SENDER_ID = ?1")
    void deleteRequests(Long id);
}
