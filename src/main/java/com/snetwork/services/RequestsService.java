package com.snetwork.services;

import com.snetwork.entities.Request;
import com.snetwork.entities.User;
import com.snetwork.repositories.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestsService {
    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private UsersService usersService;

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


    /**
     * Obtiene todas las peticiones de amistad que recibe el usuario logeado
     * @param pageable la paginacion
     * @param id el id del usuario logeado
     * @return los usuaios que mandaron la peticion de amistad
     */
    public Page<User> getFriendRequests(Pageable pageable, Long id) {
        Page<Request> receivedRequests = getReceivedRequests(pageable, id);
        List<User> friendRequests = new ArrayList<>();
        for (Request request : receivedRequests) {
            User user = usersService.getUserById(request.getSender().getId()).get();
            user.setStatus(User.ACCEPT_FRIEND_REQUEST);
            friendRequests.add(user);
        }
        return new PageImpl<>(friendRequests);
    }

    /**
     * This method has the logic of the button
     * @param idSender The friend selected in the table
     * @param principal The user logged
     */
    public void buttonAction(Long idSender, Principal principal) {
        User user = usersService.getUserByEmail(principal.getName());
        acceptFriendRequest(idSender, user.getId());
    }
}
