package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.models.UserDto;
import at.refugeescode.rcstore.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RegisterControllerServiceImpTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterService registerService;

    @Value("${admin.email}")
    private String adminEmail;

    @Test
    void userAlreadyExisted() {
        Optional<User> optionalUser = userRepository.findOneByEmail(adminEmail);
        assertTrue(optionalUser.isPresent());

        UserDto testingUser = new UserDto();
        testingUser.setEmail(adminEmail);
        String actualReturn = registerService.controlUser(testingUser);
        assertEquals("redirect:/register", actualReturn);
    }

    @Test
    void createNewUser() {
        UserDto newUserDto = createNewUserDto();
        newUserDto.setMatchingPassword("same");

        assertFalse(userRepository.findOneByEmail(newUserDto.getEmail()).isPresent());
        String actualReturn = registerService.controlUser(newUserDto);
        assertEquals("redirect:/login", actualReturn);
    }

    @Test
    void passwordsDoesntMatch() {
        UserDto newUserDto = createNewUserDto();
        newUserDto.setMatchingPassword("different");

        assertFalse(userRepository.findOneByEmail(newUserDto.getEmail()).isPresent());
        String actualReturn = registerService.controlUser(newUserDto);
        assertEquals("redirect:/register", actualReturn);
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
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }
    }

}
