package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddItemServiceImp implements AddItemService {

    private final ItemRepository itemRepository;

    @Override
    public void add(Item newItem) {
        itemRepository.save(newItem);
    }

}
