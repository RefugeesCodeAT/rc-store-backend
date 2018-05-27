package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.UserRepository;
import at.refugeescode.rcstore.persistence.model.User;
import at.refugeescode.rcstore.persistence.model.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UsersServiceImpTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersService usersService;

    @Value("${admin.email}")
    private String adminEmail;

    @Test
    void userAlreadyExisted() {
        Optional<User> optionalUser = userRepository.findOneByEmail(adminEmail);
        assertTrue(optionalUser.isPresent());

        UserDto testingUser = new UserDto();
        testingUser.setEmail(adminEmail);
        Boolean actualReturn = usersService.register(testingUser);
        assertFalse(actualReturn);
    }

    @Test
    void createNewUser() {
        UserDto newUserDto = createNewUserDto();
        newUserDto.setMatchingPassword("same");

        assertFalse(userRepository.findOneByEmail(newUserDto.getEmail()).isPresent());
        Boolean actualReturn = usersService.register(newUserDto);
        assertTrue(actualReturn);
    }

    @Test
    void passwordsDoesntMatch() {
        UserDto newUserDto = createNewUserDto();
        newUserDto.setMatchingPassword("different");

        assertFalse(userRepository.findOneByEmail(newUserDto.getEmail()).isPresent());
        Boolean actualReturn = usersService.register(newUserDto);
        assertFalse(actualReturn);
    }

    private UserDto createNewUserDto() {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("trythis@rc.com");
        newUserDto.setFirstName("test");
        newUserDto.setLastName("user");
        newUserDto.setPassword("same");
        findAndDelete(newUserDto);
        return newUserDto;
    }

    private void findAndDelete(UserDto newUserDto) {
        Optional<User> optionalUser = userRepository.findOneByEmail(newUserDto.getEmail());
        optionalUser.ifPresent(user -> userRepository.delete(user));
    }

}