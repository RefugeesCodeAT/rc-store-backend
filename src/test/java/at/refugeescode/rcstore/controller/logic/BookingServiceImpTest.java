package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.models.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookingServiceImpTest {

    @Autowired
    private BookingServiceImp bookingServiceImp;
    @MockBean
    private LoggedInUserUtility loggedInUserUtility;
    private static Item item;
    private static User user;

    @BeforeAll
    static void setItem() {
        item = Item.builder()
                .name("name")
                .description("description")
                .borrowed(false)
                .bookedBy("")
                .borrowingDate(LocalDateTime.of(2018, 5, 12, 0, 0))
                .borrowingLimit(1)
                .build();

        user = User.builder()
                .id("xx-xx-xx")
                .firstName("name")
                .lastName("last")
                .email("someone")
                .build();
    }

    @Test
    @WithMockUser("someone")
    void setBorrowingInfo() {
        Mockito.when(loggedInUserUtility.getLoggedOnUser()).thenReturn(user);
        bookingServiceImp.setBorrowingInfo(item, user);

        Item result = Item.builder()
                .name("name")
                .description("description")
                .borrowed(true)
                .bookedBy("someone")
                .borrowingDate(item.getBorrowingDate())
                .borrowingLimit(1)
                .build();

        assertEquals(item, result);
    }

    @Test
    @WithMockUser("someone")
    void testBookingLogEntry() {
        LogEntry actualLogEntry = bookingServiceImp.createLogEntry(item, user);
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
    }

}