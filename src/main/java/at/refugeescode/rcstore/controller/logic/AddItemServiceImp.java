package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ImageHandler;
import at.refugeescode.rcstore.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AddItemServiceImp implements AddItemService {

    private final ItemRepository itemRepository;
    private final ImageHandler imageHandler;

    @Override
    public void add(MultipartFile image, Item newItem) throws IOException {
        String imageID = imageHandler.save(image);
        newItem.setImageID(imageID);
        itemRepository.save(newItem);
    }

}
