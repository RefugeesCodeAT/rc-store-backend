package at.refugeescode.rcstore.persistence;

import at.refugeescode.rcstore.persistence.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findAllByBookedBy(String email);

}
