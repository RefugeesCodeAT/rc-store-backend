package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

    private final ItemRepository itemRepository;

    @Override
    public String book(Item item) {
        if (isWithinBorrowingLimit(item)) {
            setBorrowingInfo(item);
            itemRepository.save(item);
        }
        return "redirect:/";
    }

    private boolean isWithinBorrowingLimit(Item item) {
        return Duration.between(item.getBorrowingDate(), item.getDueDate()).abs().toDays() <= item.getBorrowingLimit();
    }

    private void setBorrowingInfo(Item item) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        item.setBookedBy(authentication.getName());
        item.setBorrowed(true);
    }

}
