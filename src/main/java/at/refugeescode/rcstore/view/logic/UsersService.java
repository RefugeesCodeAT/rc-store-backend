package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.model.User;
import at.refugeescode.rcstore.persistence.model.UserDto;

public interface UsersService {

    User getLoggedOnUser();

    Boolean isLoggedOnUserAdmin();

    Boolean register(UserDto userDto);

}
