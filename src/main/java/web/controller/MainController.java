package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.User;
import web.service.UserService;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String showUsersTable(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index.html";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping(value = "/adduser")
    public String addUser(@Validated User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userService.addUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated User user,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        userService.updateUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
}
