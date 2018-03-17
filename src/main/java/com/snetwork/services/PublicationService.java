package com.snetwork.services;

import com.snetwork.entities.Publication;
import com.snetwork.entities.User;
import com.snetwork.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    public void addPublication(Publication publication) {
        publicationRepository.save(publication);
    }

    public Page<Publication> getUserPublications(Pageable pageable, User user) {
        return publicationRepository.findByCreator(pageable, user);
    }

    public Page<Publication> getFriendsPublications(Pageable pageable, User user) {
        return publicationRepository.findByFriends(pageable, user);
    }
}
