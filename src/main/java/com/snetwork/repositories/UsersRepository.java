package com.snetwork.repositories;

import com.snetwork.entities.model.Request;
import com.snetwork.entities.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User,Long>{
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE ID <> ?1")
    Page<User> findAllMinusTarget(Pageable pageable, Long id);
    @Query("SELECT u FROM User u,Request r WHERE (ID_RECEIVER = ?1 OR ID_SENDER = ?1) AND ACCEPTED = TRUE" +
            " AND (ID_RECEIVER = u.id OR ID_SENDER = u.id) AND u.id <> ?1")
    Page<User> findFriends(Pageable pageable, Long id);
    @Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE CONCAT('%',LOWER(?1) ,'%') OR LOWER(u.email) LIKE CONCAT('%',LOWER(?1) ,'%')) AND " +
            "(u.id <> ?2)")
    Page<User> searchUser(Pageable pageable, String searchText, Long id);
}
