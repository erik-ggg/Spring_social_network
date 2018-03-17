package com.snetwork.services;

import com.snetwork.entities.Request;
import com.snetwork.repositories.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestsService {
    @Autowired
    private RequestsRepository requestsRepository;

    public void addFriendsQuest(Request friends) {
        requestsRepository.save(friends);
    }

    public Optional<Request> getFriendById(Long id) {
        return requestsRepository.findById(id);
    }

    /**
     * Accept the friend request
     * @param senderId the user that send the request
     * @param receiverId the user that receives the request
     */
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        requestsRepository.acceptFriendRequest(senderId, receiverId);
    }

    public Page<Request> getReceivedRequests(Pageable pageable, Long id) {
        return requestsRepository.findByReceiverId(pageable, id);
    }

    public Page<Request> getFriends(Pageable pageable, Long id) {
        return requestsRepository.findFriends(pageable, id);
    }

    public Long getFriendIdFromRequest(Request request, Long id) {
        if (request.getSender().getId() == id) return request.getReceiver().getId();
        return request.getSender().getId();
    }
}
