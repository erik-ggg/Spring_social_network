package com.snetwork.controllers;

import com.snetwork.entities.User;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class AdminController {
    @Autowired
    private UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @RequestMapping(value = { "/admin/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        @SuppressWarnings("unused")
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "admin/home";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "admin/login";
    }

    @RequestMapping(value = { "/admin/list" }, method = RequestMethod.GET)
    public String adminHome(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByEmail(dni);
        logger.info("El admin " + activeUser.getName() + " ha revisado a los usuarios.");
        Page<User> users = usersService.getOthersUsers(pageable, activeUser);
        model.addAttribute("usersList", users.getContent());
        model.addAttribute("page", users);
        return "admin/list";
    }

    @SuppressWarnings("finally")
    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id, Principal principal) {
        try {
            usersService.deleteUser(id);
            logger.info("El admin " + principal.getName() + " ha borrado al usuario con id " + id);
        }catch (NullPointerException e) {
            logger.error("Error al borrar el usuario");
        }
        finally {
            return "redirect:/admin/list";
        }
    }
}
