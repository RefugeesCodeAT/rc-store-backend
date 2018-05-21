package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemInfoServiceImp implements ItemInfoService {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> getOneItem(String id) {
        return itemRepository.findById(id);
    }

}
