package at.refugeescode.rcstore.controller.logic;

import at.refugeescode.rcstore.models.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookingServiceImpTest {

    @Autowired
    private BookingServiceImp bookingServiceImp;
    private static Item item;

    @BeforeAll
    static void setItem() {
        item = Item.builder()
                .name("name")
                .description("description")
                .borrowed(false)
                .bookedBy("")
                .borrowingDate(LocalDateTime.of(2018, 5, 12, 0, 0))
                .dueDate(LocalDateTime.of(2018, 5, 17, 0, 0))
                .borrowingLimit(1)
                .build();
    }

    @Test
    void isWithinBorrowingLimit() {
        boolean resultOne = bookingServiceImp.isWithinBorrowingLimit(item);
        assertFalse(resultOne);
        item.setBorrowingLimit(10);
        boolean resultTwo = bookingServiceImp.isWithinBorrowingLimit(item);
        assertTrue(resultTwo);
    }

    @Test
    @WithMockUser("someone")
    void setBorrowingInfo() {
        bookingServiceImp.setBorrowingInfo(item);

        Item result = Item.builder()
                .name("name")
                .description("description")
                .borrowed(true)
                .bookedBy("someone")
                .borrowingDate(item.getBorrowingDate())
                .dueDate(LocalDateTime.of(2018, 5, 17, 0, 0))
                .borrowingLimit(1)
                .build();

        Assertions.assertEquals(item, result);
    }

}