package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.models.UserDto;
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
public class RegisterServiceImp implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public String controlUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findOneByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            return "redirect:/register";
        }
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            return "redirect:/register";
        }
        register(userDto);
        return "redirect:/login";
    }

    private void register(UserDto userDto) {
        createNewUserFrom(userDto);
    }

    private void createNewUserFrom(UserDto userDto) {
        User newUser = User.builder().firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(encode(userDto.getPassword()))
                .roles(setRolesToUser())
                .build();
        userRepository.save(newUser);
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }

    private Set<String> setRolesToUser() {
        return Stream.of("ROLE_USER").collect(Collectors.toSet());
    }

}
