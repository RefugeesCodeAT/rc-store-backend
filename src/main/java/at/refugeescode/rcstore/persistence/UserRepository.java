package at.refugeescode.rcstore.persistence;

import at.refugeescode.rcstore.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByEmail(String email);

}
