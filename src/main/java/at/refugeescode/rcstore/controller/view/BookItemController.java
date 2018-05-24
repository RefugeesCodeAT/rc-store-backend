package at.refugeescode.rcstore.controller.view;

import at.refugeescode.rcstore.controller.logic.BookingService;
import at.refugeescode.rcstore.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookitem")
public class BookItemController {

    private final BookingService bookingService;

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public String book(Item item) {
        bookingService.book(item);
        return "redirect:/";
    }

}
