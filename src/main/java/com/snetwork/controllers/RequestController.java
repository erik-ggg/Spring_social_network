package com.snetwork.controllers;

import com.snetwork.entities.User;
import com.snetwork.services.RequestsService;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
@Controller
public class RequestController {
    @Autowired
    private RequestsService requestsService;

    @Autowired
    private UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @RequestMapping(value = "/requests/list")
    private String listRequestsReceived(Principal principal, Model model, Pageable pageable) {
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha revisado sus peticiones de amistad recibidas.");
        Page<User> requests = requestsService.getFriendRequests(pageable, user.getId());
        model.addAttribute("requestList", requests.getContent());
        model.addAttribute("page", requests);
        return "requests/list";
    }
    @RequestMapping(value = "/friends")
    private String listFriends(Principal principal, Model model, Pageable pageable) {
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha revisado sus amigos.");
        Page<User> friends = usersService.getFriends(pageable, user.getId());
        model.addAttribute("friends", friends.getContent());
        model.addAttribute("page", friends);
        return  "friends/friends";
    }

    @SuppressWarnings("finally")
	@RequestMapping(value = "/requests/list/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            logger.info("El usuario " + principal.getName() + " ha enviado una peticion de amistad.");
            requestsService.buttonAction(id, principal);
        }catch (NullPointerException e) {
            logger.error("El usuario " + principal.getName() + " ha producido un error al enviar una peticion de amistad.");
        }
        finally {
            return "redirect:/requests/list";
        }
    }
}
