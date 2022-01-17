package myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import myapp.model.User;
import myapp.service.RoleService;
import myapp.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }

    @GetMapping("/{id}/userInfo")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/userInfo";
    }

    @GetMapping("/newUser")
    public String createUser(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/newUser";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/editTable")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("existingUser", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/editTable";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("existingUser") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
