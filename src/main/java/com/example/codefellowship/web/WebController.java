package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
import com.example.codefellowship.infrastructure.ApplicationUserRepository;
import com.example.codefellowship.infrastructure.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class WebController{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    //lab17
    @Autowired
    private PostRepository postRepository;


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

    //**************************************************Lab17**********************************************

    @PostMapping("/post")
    public RedirectView post(@RequestParam String body, Principal p, Model m) {

        Format formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String createdAt = formatDate.format(new Date());

        Post newPost = new Post( body , createdAt);
        newPost.applicationUser = (ApplicationUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile")
    public String showMyProfile(Principal p, Model m) {
        ApplicationUser user = (ApplicationUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();

        List<Post> posts = applicationUserRepository.findById(user.id).get().posts;
        if (posts.size() > 0) {m.addAttribute("posts", posts);}
        m.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/users")
    String getAllUsers(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username" , userDetails.getUsername());
        model.addAttribute("users", applicationUserRepository.findAll());
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String viewProfile(@PathVariable long userId, Principal p, Model m) {
        //getUsername(p, m);
        List<Post> posts = applicationUserRepository.findById(userId).get().posts;
        if (posts.size() > 0) {m.addAttribute("posts", posts);}
        m.addAttribute("user", applicationUserRepository.findById(userId).get());
        return "profile";
    }

    //***********************************************lab18****************************************************

    @GetMapping("/home")
    public String myHomePage() {
        return "home";
    }

    @Transactional
    @GetMapping("/follow/{id}")
    RedirectView showFollowSuccessScreen(@PathVariable("id") long id, Model model) {

        ApplicationUser usertofollow = applicationUserRepository.findById(id).orElseThrow();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(userDetails.getUsername());

        currentUser.getFollowers().add(usertofollow);
        usertofollow.getFollowing().add(currentUser);

        applicationUserRepository.save(usertofollow);
        applicationUserRepository.save(currentUser);

        return new RedirectView("/home");
    }

    @GetMapping("/feed")
    public String getFeed(Principal accountDetail, Model model) {
        model.addAttribute("user", applicationUserRepository.findByUsername(accountDetail.getName()));
        return "feed";
    }
}
