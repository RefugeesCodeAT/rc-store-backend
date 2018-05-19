package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.RegisterService;
import at.refugeescode.rcstore.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class Register {

    private final RegisterService registerService;

    @PostMapping
    public User register(@RequestBody User newUser) {
        return registerService.register(newUser);
    }

}
