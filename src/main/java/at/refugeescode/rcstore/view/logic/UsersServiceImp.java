package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.UserRepository;
import at.refugeescode.rcstore.persistence.model.User;
import at.refugeescode.rcstore.persistence.model.UserDto;
import at.refugeescode.rcstore.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UsersServiceImp implements UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public User getLoggedOnUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser();
    }

    public Boolean isLoggedOnUserAdmin() {
        return getLoggedOnUser().getRoles().contains("ROLE_ADMIN");
    }

    public Boolean register(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findOneByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            return false;
        }
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            return false;
        }
        saveNewUserFrom(userDto);
        return true;
    }

    private void saveNewUserFrom(UserDto userDto) {
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
