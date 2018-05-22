package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;

import java.util.List;

public interface MyItemService {

    List<Item> getMyItems();

    String returnItem(String id);

}
