package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.models.User;
import at.refugeescode.rcstore.persistence.ItemRepository;
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
    private MyItemServiceImp myItemServiceImp;
    @Autowired
    private ItemRepository itemRepository;
    @MockBean
    private LoggedInUserUtility loggedInUserUtility;
    private static Item item;
    private static User user;

    @BeforeAll
    static void setItem() {
        item = Item.builder()
                .id("xx-xx-xx-xx")
                .name("name")
                .description("description")
                .borrowed(true)
                .bookedBy("some dude")
                .borrowingDate(LocalDateTime.of(2018, 5, 12, 0, 0))
                .dueDate(LocalDateTime.of(2018, 5, 17, 0, 0))
                .borrowingLimit(10)
                .build();

        user = User.builder()
                .id("xx-xx-xx")
                .firstName("name")
                .lastName("last")
                .email("some dude")
                .build();
    }

    @Test
    @WithMockUser("someone")
    void returnItem() {
        Mockito.when(loggedInUserUtility.getLoggedOnUser()).thenReturn(user);
        itemRepository.save(item);

        List<Item> tryOne = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(1, tryOne.size());

        myItemServiceImp.returnItem(item.getId());

        List<Item> tryTwo = itemRepository.findAllByBookedBy(item.getBookedBy());
        assertEquals(0, tryTwo.size());

        Item actual = itemRepository.findById(MyItemServiceImpTest.item.getId()).get();
        Item expected = Item.builder()
                .id("xx-xx-xx-xx")
                .name("name")
                .description("description")
                .borrowed(false)
                .bookedBy(null)
                .borrowingDate(null)
                .dueDate(null)
                .borrowingLimit(10)
                .build();

        assertEquals(expected, actual);

        itemRepository.delete(item);
    }

    @Test
    @WithMockUser("someone")
    void createLogEntry() {
        Mockito.when(loggedInUserUtility.getLoggedOnUser()).thenReturn(user);

        LogEntry actual = myItemServiceImp.createLogEntry(item);
        LogEntry expected = LogEntry.builder()
                .borrowerName(user.getFirstName() + " " + user.getLastName())
                .borrowerId(user.getId())
                .nameOfBorrowedItem(item.getName())
                .descriptionOfBorrowedItem(item.getDescription())
                .idOfBorrowedItem(item.getId())
                .dateOfBorrowing(item.getBorrowingDate())
                .dateOfReturn(actual.getDateOfReturn())
                .operationOnGoing(false)
                .build();

        assertEquals(expected, actual);
    }

}