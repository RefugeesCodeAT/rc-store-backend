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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User newUser) {
        Optional<User> optionalUser = userRepository.findOneByEmail(newUser.getEmail());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        setPasswordAndRoles(newUser);
        return userRepository.save(newUser);
    }

    private void setPasswordAndRoles(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<String> userRoles = Stream.of("ROLE_USER").collect(Collectors.toSet());
        newUser.setRoles(userRoles);
    }

}
