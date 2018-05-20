package at.refugeescode.rcstore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String id;
    private String name;
    private String description;
    private byte[] image;
    private int borrowingLimit;
    private Boolean borrowed;
    private String bookedBy;
    private LocalDateTime borrowingDate;
    private LocalDateTime dueDate;

}
