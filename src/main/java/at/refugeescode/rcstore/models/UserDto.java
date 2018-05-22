package at.refugeescode.rcstore.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

}