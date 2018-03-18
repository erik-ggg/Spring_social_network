package com.snetwork.controllers;

import com.snetwork.entities.Request;
import com.snetwork.entities.User;
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
        Page<User> requests = requestsService.getFriendRequests(pageable, user.getId());
        model.addAttribute("requestList", requests.getContent());
        model.addAttribute("page", requests);
        return "requests/list";
    }
    @RequestMapping(value = "/friends")
    private String listFriends(Principal principal, Model model, Pageable pageable) {
        User user = usersService.getUserByEmail(principal.getName());
        Page<User> friends = usersService.getFriends(pageable, user.getId());
        model.addAttribute("friends", friends.getContent());
        model.addAttribute("page", friends);
        return  "friends/friends";
    }

    @RequestMapping(value = "/requests/list/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            requestsService.buttonAction(id, principal);
        }catch (NullPointerException e) {
            // lanzar mensaje al usuario
            System.out.println("Error por id o amigo incorrecto");
        }
        finally {
            return "redirect:/requests/list";
        }
    }
}
