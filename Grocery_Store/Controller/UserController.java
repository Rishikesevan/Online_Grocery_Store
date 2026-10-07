package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.User;
import com.project.Grocery_Store.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "Login";
    }

    @PostMapping("/api/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        List<User> user = userService.checkUser(email);

        for(User user1 : user) {
            if (user1.getEmail().equals(email) && user1.getPassword().equals(password)) {
                session.setAttribute("userId", user1.getId());
                return "redirect:/api/Homepage";
            }
        }
        return "redirect:/login?error";

    }


    @GetMapping("/api/Signup")
    public String signUp(Model model){
        model.addAttribute("userData", new User());
        return "Sign Up";
    }
    @PostMapping("/api/user/save")
    public String savaUserData(@ModelAttribute User userData){
        userService.saveUser(userData);
        return "redirect:/login?success";
    }

}
