package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.RegisterService;
import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class Register {

    private final UserRepository userRepository;
    private final RegisterService registrationService;

    @PostMapping
    public String register(@RequestBody User newUser) {
        Optional<User> optionalUser = userRepository.findOneByEmail(newUser.getEmail());
        if (optionalUser.isPresent()) {
            return "/register";
        }
        registrationService.register(newUser);
        return "/login";
    }

}
