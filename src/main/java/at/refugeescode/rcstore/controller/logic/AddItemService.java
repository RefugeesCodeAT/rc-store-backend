package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AddItemService {

    void add(MultipartFile image, Item newItem) throws IOException;

}
