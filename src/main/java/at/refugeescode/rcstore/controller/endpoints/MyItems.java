package at.refugeescode.rcstore.controller.endpoints;

import at.refugeescode.rcstore.controller.logic.MyItemService;
import at.refugeescode.rcstore.models.Item;
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
public class MyItems {

    private final MyItemService myItemService;

    @GetMapping
    @RolesAllowed("ROLE_USER")
    public String myItems() {
        return "myitems";
    }

    @ModelAttribute("myitems")
    @RolesAllowed("ROLE_USER")
    public List<Item> myitems() {
        return myItemService.getMyItems();
    }

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String returnItem(@PathVariable String id) {
        myItemService.returnItem(id);
        return "redirect:/myitems";
    }

}
