package com.snetwork.services;

import com.snetwork.entities.data.Friend;
import com.snetwork.entities.model.Request;
import com.snetwork.entities.model.User;
import com.snetwork.repositories.RequestsRepository;
import com.snetwork.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public Page<User> getAllMinusTarget(Pageable pageable, Long id) {
        return usersRepository.findAllMinusTarget(pageable, id);
    }

    public Page<Friend> getOthersUsers(Pageable pageable, User user) {
        LinkedList<Friend> friends = new LinkedList<>();
        Page<Request> friendRequests = getFriendsRequest(pageable, user.getId());
        boolean sendedRequest = false;
        for (User item : getAllMinusTarget(pageable, user.getId())) {
            for (Request request : friendRequests) {
                if (request.getIdSender() == item.getId() && request.getIdReceiver() == item.getId()) {
                    if (request.isAccepted()) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.FRIENDS));
                    else friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.SENDED_FRIEND_REQUEST));
                    sendedRequest = true;
                }
                else if (request.getIdReceiver() == item.getId() && request.getIdSender() == item.getId()) {
                    if (request.isAccepted()) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.FRIENDS));
                    else friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.ACCEPT_FRIEND_REQUEST));
                    sendedRequest = true;
                }
            }
            if (!sendedRequest) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.SEND_FRIEND_REQUEST));
            sendedRequest = false;
        }
        return new PageImpl<>(friends);
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    private Page<Request> getFriendsRequest(Pageable pageable, Long id) {
        return requestsRepository.findByUserId(pageable, id);
    }
}
