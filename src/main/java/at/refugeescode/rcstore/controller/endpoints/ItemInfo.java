package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.ItemInfoService;
import at.refugeescode.rcstore.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequiredArgsConstructor
@RequestMapping("/iteminfo")
public class ItemInfo {

    private final ItemInfoService itemInfoService;

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String done(@PathVariable String id) {
        return itemInfoService.getOneItem(id);
    }

    @ModelAttribute("item")
    @RolesAllowed("ROLE_USER")
    public Item getItem() {
        return itemInfoService.getItem();
    }

}
