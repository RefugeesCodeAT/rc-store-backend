package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.ItemRepository;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.security.LoggedInUserUtility;
import org.junit.jupiter.api.BeforeAll;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MyItemServiceImpTest {

    @Autowired
    private LogEntryRepository logEntryRepository;
    @Autowired
    private MyItemServiceImp myItemServiceImp;
    @Autowired
    private ItemRepository itemRepository;
    @MockBean
    private LoggedInUserUtility loggedInUserUtility;

    private static Item item;
    private static User user;
    private static LogEntry logEntry;

    @BeforeAll
    static void setItem() {
        item = Item.builder()
                .id("xx-xx-xx-xx")
                .name("name")
                .description("description")
                .borrowed(true)
                .bookedBy("someone")
                .borrowingDate(LocalDateTime.of(2018, 5, 12, 0, 0))
                .borrowingLimit(10)
                .build();

        user = User.builder()
                .id("xx-xx-xx")
                .firstName("name")
                .lastName("last")
                .email("someone")
                .build();

        logEntry = LogEntry.builder()
                .id("xx-xx")
                .borrowerName("name last")
                .borrowerId("xx-xx-xx")
                .nameOfBorrowedItem("name")
                .descriptionOfBorrowedItem("description")
                .idOfBorrowedItem("xx-xx-xx-xx")
                .dateOfBorrowing(LocalDateTime.now())
                .operationOnGoing(true)
                .build();
    }

    @Test
    @WithMockUser("someone")
    void returnItem() {
        Mockito.when(loggedInUserUtility.getLoggedOnUser()).thenReturn(user);
        itemRepository.save(item);
        logEntryRepository.save(logEntry);

        List<Item> tryOne = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(1, tryOne.size());

        myItemServiceImp.returnItem(item.getId());

        List<Item> tryTwo = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(0, tryTwo.size());

        Item actualItem = itemRepository.findById(MyItemServiceImpTest.item.getId()).get();
        Item expectedItem = Item.builder()
                .id("xx-xx-xx-xx")
                .name("name")
                .description("description")
                .borrowed(false)
                .bookedBy(null)
                .borrowingDate(null)
                .borrowingLimit(10)
                .build();

        assertEquals(expectedItem, actualItem);

        LogEntry actualLogEntry = logEntryRepository.findByBorrowerIdAndIdOfBorrowedItemAndOperationOnGoing
                (user.getId(), item.getId(), false).get();
        logEntry.setDateOfReturn(actualLogEntry.getDateOfReturn());
        logEntry.setOperationOnGoing(false);

        assertEquals(logEntry, actualLogEntry);

        logEntryRepository.delete(MyItemServiceImpTest.logEntry);
        itemRepository.delete(item);
    }

}
