package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping()
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String printUsers(Model model) {
        model.addAttribute("user", userService.printUsers());
        return "users";
    }

    @GetMapping("/admin/users/user_info/{id}")
    public String user(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user_info";
    }

    @GetMapping("/user")
    public String pageUser(Principal principal, Model model) {
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String pageAdmin(Principal principal, Model model) {
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getById(id));
        return "edit";
    }

    @PostMapping("/admin/users/edit/{id}")//
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    @RequestMapping("/admin/delete/{id}")
    public String delete(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.delete(user);
        return "redirect:/admin/users";
    }
}
