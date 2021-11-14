package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {

//    @RequestMapping(value = "hello", method = RequestMethod.GET)
//    public String printWelcome(ModelMap model) {
//        List<String> messages = new ArrayList<>();
//        messages.add("Hello!");
//        messages.add("I'm Spring MVC-SECURITY application");
//        messages.add("5.2.0 version by sep'19 ");
//        model.addAttribute("messages", messages);
//        return "hello";
//    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


    //add
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public ModelAndView allUsers() {
        List<User> userList = userService.getAll();
        List<Role> roles = roleService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }


    @GetMapping("/admin/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAll());
        return "new_user";
    }

    @PostMapping("/admin/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("addRoles") String[] roles) {
        userService.create(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteUserForm(@PathVariable int id) {
        userService.delete(userService.readById(id));
        return "redirect:/admin/users";
    }

}