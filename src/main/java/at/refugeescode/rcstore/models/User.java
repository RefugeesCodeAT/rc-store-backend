package at.refugeescode.rcstore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

}
