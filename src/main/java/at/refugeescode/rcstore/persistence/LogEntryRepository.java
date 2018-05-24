package at.refugeescode.rcstore.persistence;

import at.refugeescode.rcstore.models.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {

    Optional<LogEntry> findByBorrowerIdAndIdOfBorrowedItemAndOperationOnGoing(String borrowerId,
                                                                              String idOfBorrowedItem,
                                                                              Boolean operationOnGoing);

}
