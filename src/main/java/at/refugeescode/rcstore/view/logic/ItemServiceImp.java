package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.ImageHandler;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.persistence.model.LogEntry;
import at.refugeescode.rcstore.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {

    private final ItemRepository itemRepository;
    private final ImageHandler imageHandler;
    private final UsersService usersService;
    private final LogEntryRepository logEntryRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void add(MultipartFile image, Item newItem) throws IOException {
        if (!image.isEmpty()) {
            String imageID = imageHandler.save(image);
            newItem.setImageID(imageID);
        }
        itemRepository.save(newItem);
    }

    @Override
    public Optional<Item> getOneItem(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public void book(Item item) {
        User loggedOnUser = usersService.getLoggedOnUser();
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

    @Override
    public List<Item> getMyItems() {
        User user = usersService.getLoggedOnUser();
        return itemRepository.findAllByBookedBy(user.getEmail());
    }

    @Override
    public void returnItem(String id) {
        Item item = itemRepository.findById(id).get();
        User user = usersService.getLoggedOnUser();
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
