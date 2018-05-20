package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemInfoServiceImp implements ItemInfoService {

    private final ItemRepository itemRepository;
    @Getter
    private Item item;

    @Override
    public String getOneItem(String id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            item = optionalItem.get();
            return "item";
        }
        return "redirect:/";
    }

}
