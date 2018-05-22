package at.refugeescode.rcstore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {

    @Id
    private String id;
    private String borrowerName;
    private String borrowerId;
    private String nameOfBorrowedItem;
    private String descriptionOfBorrowedItem;
    private String idOfBorrowedItem;
    private LocalDateTime dateOfBorrowing;
    private LocalDateTime dateOfReturn;
    private int daysToReturnDate;

}
