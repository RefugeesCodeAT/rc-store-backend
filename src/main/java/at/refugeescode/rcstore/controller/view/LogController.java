package at.refugeescode.rcstore.controller.view;

import at.refugeescode.rcstore.models.LogEntry;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

    private final LogEntryRepository logEntryRepository;

    @ModelAttribute("logEntries")
    List<LogEntry> logEntries() {
        return logEntryRepository.findAll();
    }

    @GetMapping
    public String get() {
        return "log";
    }

}
