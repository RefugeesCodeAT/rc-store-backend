package at.refugeescode.rcstore.controller.endpoints;

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
public class Register {

    private final RegisterService registerService;

    @GetMapping
    public String get() {
        return "register";
    }

    @ModelAttribute("newUser")
    UserDto newUser() {
        return new UserDto();
    }

    @PostMapping
    public String post(UserDto newUser) {
        return registerService.controlUser(newUser);
    }

}
