package at.refugeescode.rcstore.controller.view;

import at.refugeescode.rcstore.controller.logic.RegisterService;
import at.refugeescode.rcstore.models.UserDto;
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

    private final RegisterService registerService;

    @ModelAttribute("newUser")
    UserDto newUser() {
        return new UserDto();
    }

    @GetMapping
    public String get() {
        return "register";
    }

    @PostMapping
    public String post(UserDto newUser) {
        return registerService.controlUser(newUser);
    }

}
