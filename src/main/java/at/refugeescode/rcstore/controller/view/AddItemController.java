package at.refugeescode.rcstore.controller.view;

import at.refugeescode.rcstore.controller.logic.AddItemService;
import at.refugeescode.rcstore.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/additem")
public class AddItemController {

    private final AddItemService addItemService;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItemPage() {
        return "additem";
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItem(@RequestParam("image") MultipartFile image, Item newItem) throws IOException {
        addItemService.add(image, newItem);
        return "additem";
    }

    @ModelAttribute("newItem")
    @RolesAllowed("ROLE_ADMIN")
    public Item getNewItem() {
        return new Item();
    }

}
