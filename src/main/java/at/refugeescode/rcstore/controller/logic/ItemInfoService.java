package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;

import java.util.Optional;

public interface ItemInfoService {

    Optional<Item> getOneItem(String id);

}
