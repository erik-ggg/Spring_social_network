package com.snetwork.repositories;

import com.snetwork.entities.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User,Long>{
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE ID <> ?1")
    Page<User> findAllMinusTarget(Pageable pageable, Long id);
}
