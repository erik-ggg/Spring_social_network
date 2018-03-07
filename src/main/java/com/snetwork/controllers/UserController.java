package com.snetwork.controllers;

import com.snetwork.entities.User;
import com.snetwork.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute @Validated User user, BindingResult result, Model model) {
        //signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }

        //user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        //securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:/";
    }
}
