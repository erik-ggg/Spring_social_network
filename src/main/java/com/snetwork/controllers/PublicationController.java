package com.snetwork.controllers;

import com.snetwork.entities.Publication;
import com.snetwork.entities.User;
import com.snetwork.services.PublicationService;
import com.snetwork.services.SecurityService;
import com.snetwork.services.UsersService;
import org.assertj.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.*;
import java.security.Principal;

@Controller
public class PublicationController {
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    /**
     * Obtiene la vista para anadir publicaciones
     * @param model
     * @return
     */
    @RequestMapping(value = "/publications/add", method = RequestMethod.GET)
    public String addPublication(Model model) {
        model.addAttribute("publication", new Publication());
        return "publications/add";
    }

    /**
     *
     * @param publication
     * @param result
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/publications/add", method = RequestMethod.POST)
    public String addPublication(@ModelAttribute Publication publication, BindingResult result, Principal principal) throws IOException {
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha creado una publicacion.");
        publication.setUser(user);
        publication.setDate(DateUtil.now());
        publicationService.savePhoto(publication);
        publicationService.addPublication(publication);
        return "redirect:list";
    }


    /**
     * Obtiene las publicaciones del usuario conectado
     * @param model
     * @param pageable
     * @param principal
     * @return
     */
    @RequestMapping(value = "/publications/list")
    public String getUserPublications(Model model, Pageable pageable, Principal principal) {
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha revisado sus publicaciones.");
        Page<Publication> publications = publicationService.getUserPublications(pageable, user);
        model.addAttribute("publications", publications.getContent());
        model.addAttribute("page", publications);
        return "publications/list";
    }

    /**
     * Obtiene las publicaciones de los amigos del usuario conectado
     * @param model
     * @param pageable
     * @param principal
     * @return
     */
    @RequestMapping(value = "/publications/friends")
    public String getFriendsPublications(Model model, Pageable pageable, Principal principal){
        User user = usersService.getUserByEmail(principal.getName());
        logger.info("El usuario " + user.getName() + " ha revisado las publicaciones de sus amigos.");
        Page<Publication> publications = publicationService.getFriendsPublications(pageable, user);
        model.addAttribute("publications", publications.getContent());
        model.addAttribute("page", publications);
        return "publications/list";
    }
}
