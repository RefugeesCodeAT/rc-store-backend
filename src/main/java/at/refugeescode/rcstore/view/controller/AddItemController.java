package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.view.logic.ItemService;
import at.refugeescode.rcstore.view.logic.UsersService;
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

    private final ItemService itemService;
    private final UsersService usersService;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItemPage() {
        return "additem";
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public String addItem(@RequestParam("image") MultipartFile image, Item newItem) throws IOException {
        itemService.add(image, newItem);
        return "redirect:/additem";
    }

    @ModelAttribute("newItem")
    @RolesAllowed("ROLE_ADMIN")
    public Item getNewItem() {
        return new Item();
    }

    @ModelAttribute("admin")
    public boolean isUserAdmin() {
        return usersService.isLoggedOnUserAdmin();
    }

}
