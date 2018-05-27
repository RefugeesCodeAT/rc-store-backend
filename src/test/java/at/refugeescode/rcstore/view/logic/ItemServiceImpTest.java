package at.refugeescode.rcstore.view.logic;

import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.persistence.model.Item;
import at.refugeescode.rcstore.persistence.model.LogEntry;
import at.refugeescode.rcstore.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ItemServiceImpTest {

    @Autowired
    private ItemServiceImp itemServiceImp;
    @Autowired
    private LogEntryRepository logEntryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @MockBean
    private UsersService usersService;

    private Item getItem() {
        return Item.builder()
                .name("name")
                .description("description")
                .borrowingLimit(10)
                .build();
    }

    private User getUser() {
        return User.builder()
                .firstName("name")
                .lastName("last")
                .email("someone")
                .build();
    }

    private LogEntry getLogEntry() {
        return LogEntry.builder()
                .borrowerName("name last")
                .nameOfBorrowedItem("name")
                .descriptionOfBorrowedItem("description")
                .dateOfBorrowing(LocalDateTime.now())
                .build();
    }

    @Test
    @WithMockUser("someone")
    void setBorrowingInfo() {
        User user = getUser();
        user.setId("xx");
        Item item = getItem();
        item.setId("xx-xx");
        Mockito.when(usersService.getLoggedOnUser()).thenReturn(user);
        itemServiceImp.setBorrowingInfo(item, user);

        Item result = Item.builder()
                .id("xx-xx")
                .name("name")
                .description("description")
                .borrowed(true)
                .bookedBy("someone")
                .borrowingDate(item.getBorrowingDate())
                .borrowingLimit(10)
                .build();

        assertEquals(item, result);
    }

    @Test
    @WithMockUser("someone")
    void createLogEntry() {
        User user = getUser();
        user.setId("yy");
        Item item = getItem();
        item.setId("yy-yy");
        LogEntry actualLogEntry = itemServiceImp.createLogEntry(item, user);
        LogEntry expected = LogEntry.builder()
                .id(actualLogEntry.getId())
                .borrowerName(user.getFirstName() + " " + user.getLastName())
                .borrowerId(user.getId())
                .nameOfBorrowedItem(item.getName())
                .descriptionOfBorrowedItem(item.getDescription())
                .idOfBorrowedItem(item.getId())
                .dateOfBorrowing(LocalDateTime.now())
                .operationOnGoing(true)
                .dateOfBorrowing(actualLogEntry.getDateOfBorrowing())
                .build();

        assertEquals(expected, actualLogEntry);
        logEntryRepository.delete(actualLogEntry);
    }

    @Test
    @WithMockUser("someone")
    void returnItem() {
        User user = getUser();
        user.setId("cc");
        Item item = getItem();
        item.setId("cc-cc");
        item.setBookedBy("someone");
        item.setBorrowed(true);
        item.setBorrowingDate(LocalDateTime.now());

        Mockito.when(usersService.getLoggedOnUser()).thenReturn(user);
        itemRepository.save(item);

        List<Item> tryOne = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(1, tryOne.size());

        LogEntry logEntry = getLogEntry();
        logEntry.setId("cc-cc-cc");
        logEntry.setBorrowerId("cc");
        logEntry.setIdOfBorrowedItem("cc-cc");
        logEntry.setOperationOnGoing(true);
        logEntryRepository.save(logEntry);
        itemServiceImp.returnItem("cc-cc");

        List<Item> tryTwo = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(0, tryTwo.size());

        Item actualItem = itemRepository.findById(item.getId()).get();
        Item expectedItem = Item.builder()
                .id("cc-cc")
                .name("name")
                .description("description")
                .borrowed(false)
                .bookedBy(null)
                .borrowingDate(null)
                .borrowingLimit(10)
                .build();

        assertEquals(expectedItem, actualItem);

        logEntryRepository.delete(logEntry);
        itemRepository.delete(item);
    }

    @Test
    @WithMockUser("someone")
    void testReturnLogEntry() {
        User user = getUser();
        user.setId("vv");
        Item item = getItem();
        item.setId("vv-vv");
        LogEntry logEntry = getLogEntry();
        logEntry.setId("vv-vv-vv");
        logEntry.setBorrowerId("vv");
        logEntry.setIdOfBorrowedItem("vv-vv");
        logEntry.setOperationOnGoing(true);

        logEntryRepository.save(logEntry);
        itemServiceImp.updateLogEntry(item, user);
        LogEntry actualLogEntry = logEntryRepository.findByBorrowerIdAndIdOfBorrowedItemAndOperationOnGoing
                ("vv", "vv-vv", false).get();
        logEntry.setDateOfReturn(actualLogEntry.getDateOfReturn());
        logEntry.setOperationOnGoing(false);
        assertEquals(logEntry, actualLogEntry);
        logEntryRepository.delete(logEntry);
    }

}