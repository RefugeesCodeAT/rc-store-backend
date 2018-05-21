package at.refugeescode.rcstore.persistence;

import at.refugeescode.rcstore.models.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {
}
