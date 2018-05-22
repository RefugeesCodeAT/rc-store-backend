package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyItemServiceImp implements MyItemService {

    private final ItemRepository itemRepository;
    private final LogEntryRepository logEntryRepository;

    @Override
    public List<Item> getMyItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return itemRepository.findAllByBookedBy(authentication.getName());
    }

    @Override
    public String returnItem(String id) {
        Item item = itemRepository.findById(id).get();
        logReturnEntryFor(item);
        item.setBorrowed(false);
        item.setBookedBy(null);
        item.setDueDate(null);
        item.setBorrowingDate(null);
        return "redirect:/myitems";
    }

    private void logReturnEntryFor(Item item) {
        LogEntry.builder()
                .build();
    }

}
