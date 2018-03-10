package com.snetwork.repositories;

import com.snetwork.entities.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User,Long>{
    User findByEmail(String email);
}
