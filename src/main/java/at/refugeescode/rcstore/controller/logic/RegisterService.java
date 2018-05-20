package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String controlUser(User newUser){
        Optional<User> optionalUser = userRepository.findOneByEmail(newUser.getEmail());
        if (optionalUser.isPresent()) {
            return "redirect:/register";
        }
        register(newUser);
        return "redirect:/login";
    }

    private void register(User newUser) {
        encodePassword(newUser);
        setRolesToUser(newUser);
        userRepository.save(newUser);
    }

    private void encodePassword(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
    }

    private void setRolesToUser(User newUser) {
        Set<String> userRoles = Stream.of("ROLE_USER").collect(Collectors.toSet());
        newUser.setRoles(userRoles);
    }

}
