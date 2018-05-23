package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.security.LoggedInUserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        logReturnEntryFor(item);
        item.setBorrowed(false);
        item.setBookedBy(null);
        item.setDueDate(null);
        item.setBorrowingDate(null);
        itemRepository.save(item);
    }

    private void logReturnEntryFor(Item item) {
        LogEntry logEntry = createLogEntry(item);
        logEntryRepository.save(logEntry);
    }

    LogEntry createLogEntry(Item item) {
        User user = loggedInUserUtility.getLoggedOnUser();
        return LogEntry.builder()
                .borrowerName(user.getFirstName() + " " + user.getLastName())
                .borrowerId(user.getId())
                .nameOfBorrowedItem(item.getName())
                .descriptionOfBorrowedItem(item.getDescription())
                .idOfBorrowedItem(item.getId())
                .dateOfBorrowing(item.getBorrowingDate())
                .dateOfReturn(LocalDateTime.now())
                .operationOnGoing(false)
                .build();
    }

}
