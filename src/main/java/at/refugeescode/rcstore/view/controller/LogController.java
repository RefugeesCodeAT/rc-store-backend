package at.refugeescode.rcstore.view.controller;

import at.refugeescode.rcstore.persistence.model.LogEntry;
import at.refugeescode.rcstore.persistence.LogEntryRepository;
import at.refugeescode.rcstore.view.logic.UsersService;
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
    private final UsersService usersService;

    @GetMapping
    public String get() {
        return "log";
    }

    @ModelAttribute("logEntries")
    List<LogEntry> logEntries() {
        return logEntryRepository.findAll();
    }

    @ModelAttribute("admin")
    public boolean isUserAdmin() {
        return usersService.isLoggedOnUserAdmin();
    }

}
