package at.refugeescode.rcstore.controller.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class Log {

    //private final LogEntryRepository logEntryRepository;

    @ModelAttribute
    //Todo

    @GetMapping
    public String get() {
        //Todo
        return "log";
    }

}
