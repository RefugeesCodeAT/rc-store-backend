package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> getAllItems();

    void add(MultipartFile image, Item newItem) throws IOException;

    Optional<Item> getOneItem(String id);

    void book(Item item);

    List<Item> getMyItems();

    void returnItem(String id);

}
