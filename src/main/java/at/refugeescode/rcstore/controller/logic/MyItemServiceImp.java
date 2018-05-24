package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.security.LoggedInUserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyItemServiceImp implements MyItemService {

    private final ItemRepository itemRepository;
    private final LogEntryRepository logEntryRepository;
    private final LoggedInUserUtility loggedInUserUtility;

    @Override
    public List<Item> getMyItems() {
        User user = loggedInUserUtility.getLoggedOnUser();
        return itemRepository.findAllByBookedBy(user.getEmail());
    }

    @Override
    public void returnItem(String id) {
        Item item = itemRepository.findById(id).get();
        User user = loggedInUserUtility.getLoggedOnUser();
        updateLogEntry(item, user);
        item.setBorrowed(false);
        item.setBookedBy(null);
        item.setBorrowingDate(null);
        itemRepository.save(item);
    }

    private void updateLogEntry(Item item, User user) {
        LogEntry logEntry = logEntryRepository.findByBorrowerIdAndIdOfBorrowedItemAndOperationOnGoing
                (user.getId(), item.getId(), true).get();

        logEntry.setOperationOnGoing(false);
        logEntry.setDateOfReturn(LocalDateTime.now());

        logEntryRepository.save(logEntry);
    }

}
