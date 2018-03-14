package com.snetwork.services;

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

    public Page<User> getOthersUsers(Pageable pageable, User user) {
        List<Request> friendRequests = getFriendsRequest(user.getId());
        Page<User> users = getAllMinusTarget(pageable, user.getId());
        boolean sendedRequest = false;
        for (User item : users) {
            for (Request request : friendRequests) {
                if (request.getIdSender() == user.getId() && request.getIdReceiver() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(user.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(user.SENDED_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
                else if (request.getIdReceiver() == user.getId() && request.getIdSender() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(user.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(user.ACCEPT_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
            }
            if (!sendedRequest) item.setStatus(user.SEND_FRIEND_REQUEST);
            sendedRequest = false;
        }
        return users;
    }

    public Page<User> getFriendBySearch(Pageable pageable, String text, Long id) {
        List<Request> friendRequests = getFriendsRequest(id);
        Page<User> users = usersRepository.searchUser(pageable, text, id);
        boolean sendedRequest = false;
        for (User item : users) {
            for (Request request : friendRequests) {
                if (request.getIdSender() == id && request.getIdReceiver() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.SENDED_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
                else if (request.getIdReceiver() == id && request.getIdSender() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.ACCEPT_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
            }
            if (!sendedRequest) item.setStatus(User.SEND_FRIEND_REQUEST);
            sendedRequest = false;
        }
        return users;
    }

    public Page<User> getFriends(Pageable pageable, Long id) {
        return usersRepository.findFriends(pageable, id);
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

    private List<Request> getFriendsRequest(Long id) {
        return requestsRepository.findByUserId(id);
    }
}
