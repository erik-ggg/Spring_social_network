package com.snetwork.services;

import com.snetwork.entities.data.Friend;
import com.snetwork.entities.model.Friends;
import com.snetwork.entities.model.User;
import com.snetwork.repositories.FriendsRepository;
import com.snetwork.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public List<Friend> getOthersUsers(User user) {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        users.remove(user);
        List<Friend> friends = new ArrayList<>();
        List<Friends> friendRquests = getFriendsRequest(user.getId());
        boolean sendedRequest = false;
        for (User item : users) {
            for (Friends request : friendRquests) {
                if (request.getIdSender() == user.getId() && request.getIdReceiver() == item.getId()) {
                    if (request.isAccepted()) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.FRIENDS));
                    else friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.SENDED_FRIEND_REQUEST));
                    sendedRequest = true;
                }
                else if (request.getIdReceiver() == user.getId() && request.getIdSender() == item.getId()) {
                    if (request.isAccepted()) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.FRIENDS));
                    else friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.ACCEPT_FRIEND_REQUEST));
                    sendedRequest = true;
                }
            }
            if (!sendedRequest) friends.add(new Friend(item.getId(), item.getName(), item.getEmail(), Friend.SEND_FRIEND_REQUEST));
            sendedRequest = false;
        }
        return friends;
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    private List<Friends> getFriendsRequest(Long id) {
        List<Friends> friends = friendsRepository.findByIdUser(id);
        return friends;
    }
}
