package com.snetwork.services;

import com.snetwork.entities.model.Friends;
import com.snetwork.repositories.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;

    public void addFriendsQuest(Friends friends) {
        friendsRepository.save(friends);
    }

    public Optional<Friends> getFriendById(Long id) {
        return friendsRepository.findById(id);
    }

    public void acceptFriendRequest(Long senderId, Long receiverId) {
        friendsRepository.acceptFriendRequest(senderId, receiverId);
    }
}
