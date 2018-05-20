package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.RegisterService;
import at.refugeescode.rcstore.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public String post(@RequestBody User newUser) {
        return registerService.controlUser(newUser);
    }

}
