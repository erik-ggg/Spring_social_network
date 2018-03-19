package com.snetwork.repositories;

import com.snetwork.entities.Publication;
import com.snetwork.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PublicationRepository extends CrudRepository<Publication, Long> {
    @Query("SELECT p FROM Publication p WHERE p.user.id = ?1")
    Page<Publication> findByCreator(Pageable pageable, Long id);
    @Query("SELECT p FROM Publication p, Request r WHERE p.user <> ?1 AND (r.receiver = ?1 OR r.sender = ?1) " +
            "AND (r.receiver = p.user OR r.sender = p.user) AND r.accepted = TRUE")
    Page<Publication> findByFriend(Pageable pageable, User user);
}
