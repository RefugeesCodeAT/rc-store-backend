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

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

    private final ItemRepository itemRepository;
    private final LogEntryRepository logEntryRepository;
    private final LoggedInUserUtility loggedInUserUtility;

    @Override
    public void book(Item item) {
        User loggedOnUser = loggedInUserUtility.getLoggedOnUser();
        setBorrowingInfo(item, loggedOnUser);
        createLogEntry(item, loggedOnUser);
        itemRepository.save(item);
    }

    void setBorrowingInfo(Item item, User loggedOnUser) {
        item.setBorrowingDate(LocalDateTime.now());
        item.setBookedBy(loggedOnUser.getEmail());
        item.setBorrowed(true);
    }

    LogEntry createLogEntry(Item item, User loggedOnUser) {
        LogEntry logEntry = LogEntry.builder()
                .borrowerName(loggedOnUser.getFirstName() + " " + loggedOnUser.getLastName())
                .borrowerId(loggedOnUser.getId())
                .nameOfBorrowedItem(item.getName())
                .descriptionOfBorrowedItem(item.getDescription())
                .idOfBorrowedItem(item.getId())
                .dateOfBorrowing(LocalDateTime.now())
                .operationOnGoing(true)
                .build();
        logEntryRepository.save(logEntry);

        return logEntry;
    }

}
