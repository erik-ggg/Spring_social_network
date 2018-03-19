package com.snetwork.controllers;

import com.snetwork.entities.User;
import com.snetwork.services.RolesService;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import com.snetwork.validators.SignUpFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private RolesService rolesService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private SecurityService securityService;

    private Page<User> friends;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    /**
     * Envia una peticion de amistad al usuario seleccionado
     * @param id el id del usuario seleccionado
     * @param principal
     * @return
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "/home/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(@PathVariable Long id, Principal principal) {
        try {
            User friend = usersService.getFriendRequest(id, friends);
            logger.info("El usuario " + principal.getName() + " ha enviado una solicitud de amistad");
            usersService.buttonAction(friend, principal);
        }catch (NullPointerException e) {
            logger.error("Error al enviar la peticion de amistad");
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

    /**
     * Registra al usuario dentro de la aplicacion
     * @param user
     * @param result
     * @param model
     * @return
     */
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

    /**
     * Obtiene la vista principal de los usuarios cargando todos los usuarios dentro de una pagina
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByEmail(dni);
        if (activeUser.getRole().equals(rolesService.getRoles()[0])) {
            logger.info("El usuario " + activeUser.getName() + " ha logeado correctamente.");
            friends = usersService.getOthersUsers(pageable, activeUser);
            model.addAttribute("usersList", friends.getContent());
            model.addAttribute("page", friends);
            return "home";
        }
        else
            return "admin/home";
    }

    /**
     * Realiza la misma funcionalidad que home pero filtrando los usuarios por el campo de busqueda
     * @param model
     * @param principal
     * @param pageable
     * @param searchText el filtro para buscar usuarios
     * @return
     */
    @RequestMapping(value = {"/home/search"})
    public String homeSearch(Model model, Principal principal, Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha realizado una busqueda de usuarios.");
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
}
