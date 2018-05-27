package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.ImageHandler;
import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.view.logic.ItemService;
import at.refugeescode.rcstore.view.logic.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final ImageHandler imageHandler;
    private final UsersService usersService;

    @GetMapping()
    @RolesAllowed("ROLE_USER")
    public String page(Model model) {
        model.addAttribute("item", new Item());
        return "item";
    }

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String done(@PathVariable String id, Model model) {
        Optional<Item> optionalItem = itemService.getOneItem(id);
        if (!optionalItem.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("item", optionalItem.get());
        return "item";
    }

    @ResponseBody
    @RolesAllowed("ROLE_USER")
    @GetMapping("/images/{id}")
    public byte[] getImage(@PathVariable String id) {
        return imageHandler.load(id);
    }

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public String book(Item item) {
        itemService.book(item);
        return "redirect:/";
    }

    @ModelAttribute("admin")
    public boolean isUserAdmin() {
        return usersService.isLoggedOnUserAdmin();
    }

}
