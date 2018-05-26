package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.view.logic.ItemService;
import at.refugeescode.rcstore.view.logic.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/myitems")
@RequiredArgsConstructor
public class MyItemsController {

    private final ItemService itemService;
    private final UsersService usersService;

    @GetMapping
    @RolesAllowed("ROLE_USER")
    public String myItems() {
        return "myitems";
    }

    @ModelAttribute("myitems")
    @RolesAllowed("ROLE_USER")
    public List<Item> myitems() {
        return itemService.getMyItems();
    }

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String returnItem(@PathVariable String id) {
        itemService.returnItem(id);
        return "redirect:/myitems";
    }

    @ModelAttribute("admin")
    public boolean isUserAdmin() {
        return usersService.isLoggedOnUserAdmin();
    }

}
