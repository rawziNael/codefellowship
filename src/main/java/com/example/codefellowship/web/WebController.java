package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.infrastructure.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class WebController{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView getHome() {
        return new RedirectView("/");
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String dateOfBirth,
                                   @RequestParam String bio,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   Model userModel) {
        ApplicationUser applicationUser = new ApplicationUser(username,
                                                                bCryptPasswordEncoder.encode(password),
                                                                firstName,
                                                                lastName,
                                                                dateOfBirth,
                                                                bio
                );
        System.out.println(applicationUser);
        applicationUserRepository.save(applicationUser);
        return new RedirectView("/profile/" + applicationUser.id);
    }

    @GetMapping("/profile/{userId}")
    public String viewProfile(@PathVariable long userId, Model userModel) {
        //System.out.println();
        userModel.addAttribute("user", applicationUserRepository.findById(userId).get());
        return "profile";
    }

    @PostMapping("/logout")
    public RedirectView logOutUserWithSecret() {
        return new RedirectView("/login");
    }
}
