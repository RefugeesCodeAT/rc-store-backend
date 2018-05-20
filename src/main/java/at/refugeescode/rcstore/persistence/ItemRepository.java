package at.refugeescode.rcstore.persistence;

import at.refugeescode.rcstore.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
