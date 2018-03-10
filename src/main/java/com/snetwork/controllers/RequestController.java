package com.snetwork.controllers;

import com.snetwork.entities.data.Friend;
import com.snetwork.entities.model.Request;
import com.snetwork.entities.model.User;
import com.snetwork.services.RequestsService;
import com.snetwork.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class RequestController {
    @Autowired
    private RequestsService requestsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/requests/list")
    private String listRequestsReceived(Principal principal, Model model, Pageable pageable) {
        User user = usersService.getUserByEmail(principal.getName());
        Page<Friend> requests = getFriendRequests(pageable, user.getId());
        model.addAttribute("requestList", requests.getContent());
        model.addAttribute("page", requests);
        return "requests/list";
    }
    @RequestMapping(value = "/friends")
    private String listFriends(Principal principal, Model model, Pageable pageable) {
        User user = usersService.getUserByEmail(principal.getName());
        Page<Friend> friends = getFriends(pageable, user.getId());
        model.addAttribute("friends", friends.getContent());
        model.addAttribute("page", friends);
        return  "friends/friends";
    }

    @RequestMapping(value = "/requests/list/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            buttonAction(id, principal);
        }catch (NullPointerException e) {
            // lanzar mensaje al usuario
            System.out.println("Error por id o amigo incorrecto");
        }
        finally {
            return "redirect:/requests/list";
        }
    }


    private Page<Friend> getFriends(Pageable pageable, Long id) {
        Page<Request> requestsAccepted = requestsService.getFriends(pageable, id);
        LinkedList<Friend> friends = new LinkedList<>();
        for (Request request : requestsAccepted) {
            User user = usersService.getUserById(request.getIdSender()).get();
            friends.add(new Friend(user.getId(), user.getName(), user.getEmail(), Friend.FRIENDS));
        }
        return new PageImpl<>(friends);
    }

    private Page<Friend> getFriendRequests(Pageable pageable, Long id) {
        Page<Request> receivedRequests = requestsService.getReceivedRequests(pageable, id);
        List<Friend> friendRequests = new ArrayList<>();
        for (Request request : receivedRequests) {
            User user = usersService.getUserById(request.getIdSender()).get();
            friendRequests.add(new Friend(user.getId(), user.getName(), user.getEmail(), Friend.ACCEPT_FRIEND_REQUEST));
        }
        return new PageImpl<>(friendRequests);
    }

    /**
     * This method has the logic of the button
     * @param idSender The friend selected in the table
     * @param principal The user logged
     */
    private void buttonAction(Long idSender, Principal principal) {
        User user = usersService.getUserByEmail(principal.getName());
        requestsService.acceptFriendRequest(idSender, user.getId());
    }
}
