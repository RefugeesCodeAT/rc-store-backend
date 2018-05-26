package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.view.logic.ItemService;
import at.refugeescode.rcstore.view.logic.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final ItemService itemService;
    private final UsersService usersService;

    @GetMapping
    @RolesAllowed("ROLE_USER")
    public String home() {
        return "home";
    }

    @ModelAttribute("items")
    @RolesAllowed("ROLE_USER")
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    @ModelAttribute("admin")
    public boolean isUserAdmin() {
        return usersService.isLoggedOnUserAdmin();
    }

}
