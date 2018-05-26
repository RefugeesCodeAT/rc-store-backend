package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.model.UserDto;
import at.refugeescode.rcstore.view.logic.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UsersService usersService;

    @GetMapping
    public String page() {
        return "register";
    }

    @ModelAttribute("newUser")
    UserDto newUser() {
        return new UserDto();
    }

    @PostMapping
    public String registerNewUser(UserDto newUser) {
        Boolean registerSuccessful = usersService.register(newUser);
        if (registerSuccessful) {
            return "redirect:/login";
        }
        return "redirect:/register";
    }

}
