package at.refugeescode.rcstore.controller.view;

import at.refugeescode.rcstore.controller.logic.ItemInfoService;
import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/iteminfo")
public class ItemInfoController {

    private final ItemInfoService itemInfoService;
    private final ImageHandler imageHandler;

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_USER")
    public String done(@PathVariable String id, Model model) {
        Optional<Item> optionalItem = itemInfoService.getOneItem(id);
        if (!optionalItem.isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("item", optionalItem.get());
        return "item";
    }

    @ResponseBody
    @GetMapping("/images/{id}")
    public byte[] getImage(@PathVariable String id) {
        return imageHandler.load(id);
    }

}
