package com.snetwork.controllers;

import com.snetwork.entities.data.Friend;
import com.snetwork.entities.model.Friends;
import com.snetwork.entities.model.User;
import com.snetwork.services.FriendsService;
import com.snetwork.services.RolesService;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import com.snetwork.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private SecurityService securityService;

    private List<Friend> friends;

    @RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            Friend friend = getFriendRequest(id);
            buttonAction(friend, principal);
        }catch (NullPointerException e) {
            System.out.println("Error por id o amigo incorrecto");
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
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
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByEmail(dni);
        friends = usersService.getOthersUsers(activeUser);
        model.addAttribute("usersList", friends);
        return "home";
    }

    /**
     * This method has the logic of the button
     * @param friend The friend selected in the table
     * @param principal The user logged
     */
    private void buttonAction(Friend friend, Principal principal) {
        if (friend.getStatus().equals(Friend.SEND_FRIEND_REQUEST)) {
            User user = usersService.getUserByEmail(principal.getName());
            friendsService.addFriendsQuest(new Friends(user.getId(), friend.getId(), false));
        }
        else if (friend.getStatus().equals(Friend.SENDED_FRIEND_REQUEST)) System.out.println("Petición ya enviada");
        else if (friend.getStatus().equals(Friend.ACCEPT_FRIEND_REQUEST)) {
            User user = usersService.getUserByEmail(principal.getName());
            friendsService.acceptFriendRequest(friend.getId(), user.getId());
        }
        else if (friend.getStatus().equals(Friend.FRIENDS)) System.out.println("You're already friends!");;
    }


    /**
     * Retrieves the friend object from the id
     * @param id the friend id
     * @return the friend
     */
    private Friend getFriendRequest(Long id) {
        for (Friend friend : friends) {
            if (friend.getId() == id) return friend;
        }
        throw new NullPointerException("Amigo no seleccionado");
    }
}
