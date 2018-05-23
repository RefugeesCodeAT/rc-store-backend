package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.security.LoggedInUserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImp implements HomeService {

    private final ItemRepository itemRepository;
    private final LoggedInUserUtility loggedInUserUtility;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public boolean isUserAdmin() {
        return loggedInUserUtility.getLoggedOnUser().getRoles().contains("ROLE_ADMIN");
    }

}
