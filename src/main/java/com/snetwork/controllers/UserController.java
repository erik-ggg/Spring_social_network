package com.snetwork.controllers;

import com.snetwork.entities.model.Request;
import com.snetwork.entities.model.User;
import com.snetwork.services.RequestsService;
import com.snetwork.services.RolesService;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import com.snetwork.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private SecurityService securityService;

    private Page<User> friends;

    @RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            User friend = getFriendRequest(id);
            buttonAction(friend, principal);
        }catch (NullPointerException e) {
            // lanzar mensaje al usuario
            System.out.println("Error por id o amigo incorrecto");
        }
        finally {
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/elogin", method = RequestMethod.GET)
    public String elogin(Model model) {
        return "elogin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute @Validated User user, BindingResult result, Model model) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }

        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByEmail(dni);
        friends = usersService.getOthersUsers(pageable, activeUser);
        model.addAttribute("usersList", friends.getContent());
        model.addAttribute("page", friends);
        return "home";
    }

    @RequestMapping(value = {"/home/search"})
    public String homeSearch(Model model, Principal principal, Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
        User user = usersService.getUserByEmail(principal.getName());
        if (searchText != null && !searchText.isEmpty()) {
            friends = usersService.getFriendBySearch(pageable, searchText, user.getId());
        }
        else {
            friends = usersService.getOthersUsers(pageable, user);
        }

        model.addAttribute("usersList", friends.getContent());
        model.addAttribute("page", friends);
        return "home";
    }

    /**
     * This method has the logic of the button
     * @param friend The friend selected in the table
     * @param principal The user logged
     */
    private void buttonAction(User friend, Principal principal) {
        if (friend.getStatus().equals(User.SEND_FRIEND_REQUEST)) {
            User user = usersService.getUserByEmail(principal.getName());
            requestsService.addFriendsQuest(new Request(user.getId(), friend.getId(), false));
        }
        else if (friend.getStatus().equals(User.SENDED_FRIEND_REQUEST)) System.out.println("Petición ya enviada");
        else if (friend.getStatus().equals(User.ACCEPT_FRIEND_REQUEST)) {
            User user = usersService.getUserByEmail(principal.getName());
            requestsService.acceptFriendRequest(friend.getId(), user.getId());
        }
        else if (friend.getStatus().equals(User.FRIENDS)) System.out.println("You're already friends!");;
    }


    /**
     * Retrieves the friend object from the id
     * @param id the friend id
     * @return the friend
     */
    private User getFriendRequest(Long id) {
        for (User friend : friends) {
            if (friend.getId() == id) return friend;
        }
        throw new NullPointerException("Amigo no seleccionado");
    }
}
