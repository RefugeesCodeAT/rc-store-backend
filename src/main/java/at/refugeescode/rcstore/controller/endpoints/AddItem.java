package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.AddItemService;
import at.refugeescode.rcstore.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequiredArgsConstructor
@RequestMapping("/additem")
public class AddItem {

    private final AddItemService addItemService;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItemPage() {
        return "additem";
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItem(Item newItem) {
        addItemService.add(newItem);
        return "additem";
    }

    @ModelAttribute("newItem")
    @RolesAllowed("ROLE_ADMIN")
    public Item getNewItem() {
        return new Item();
    }

}
