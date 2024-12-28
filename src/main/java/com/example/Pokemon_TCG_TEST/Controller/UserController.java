package com.example.Pokemon_TCG_TEST.Controller;

import com.example.Pokemon_TCG_TEST.Model.User;
import com.example.Pokemon_TCG_TEST.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping(path = {"/",})
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User()); // Add an empty User object for the form
        return "index"; // Return the login page template
    }

    @PostMapping("/login")
    public String loginUser(
            @Valid @ModelAttribute("user") User user, // Bind and validate User object
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid login details. Please correct the errors.");
            return "index";
        }

        try {
            // Authenticate the user
            User authenticatedUser = userService.loginUser(user.getEmail(), user.getPassword());
            session.setAttribute("user", authenticatedUser); // Store the authenticated user in session
            return "redirect:/search"; // Redirect to search after successful login
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); // Add error message
            return "index"; // Return to login page
        }
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user, // Bind and validate User object
            BindingResult bindingResult, // Holds validation results
            Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid registration details. Please correct the errors.");
            return "register"; // Return to the registration page with validation errors
        }

        try {
            // Register the user
            userService.registerUser(user);
            // Redirect to the login page after successful registration
            return "redirect:/?success=You have registered successfully, please proceed to login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // Return to the registration page with the error message
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
