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

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

    private final ItemRepository itemRepository;
    private final LogEntryRepository logEntryRepository;
    private final LoggedInUserUtility loggedInUserUtility;

    @Override
    public void book(Item item) {
        if (isWithinBorrowingLimit(item)) {
            setBorrowingInfo(item);
            createLogEntry(item);
            itemRepository.save(item);
        }
    }

    boolean isWithinBorrowingLimit(Item item) {
        return Duration.between(LocalDateTime.now(), item.getDueDate()).abs().toDays() <= item.getBorrowingLimit();
    }

    void setBorrowingInfo(Item item) {
        item.setBorrowingDate(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        item.setBookedBy(authentication.getName());
        item.setBorrowed(true);
    }

    private void createLogEntry(Item item) {
        User user = loggedInUserUtility.getLoggedOnUser();
        LogEntry logEntry = LogEntry.builder()
                .borrowerName(user.getFirstName() + " " + user.getLastName())
                .borrowerId(user.getId())
                .nameOfBorrowedItem(item.getName())
                .descriptionOfBorrowedItem(item.getDescription())
                .idOfBorrowedItem(item.getId())
                .dateOfBorrowing(LocalDateTime.now())
                .dateOfReturn(item.getDueDate())
                .operationOnGoing(true)
                .build();
        logEntryRepository.save(logEntry);
    }

}
