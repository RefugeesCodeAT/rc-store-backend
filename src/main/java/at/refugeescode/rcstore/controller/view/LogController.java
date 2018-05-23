package at.refugeescode.rcstore.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

    //private final LogEntryRepository logEntryRepository;

    @ModelAttribute
    //Todo

    @GetMapping
    public String get() {
        //Todo
        return "log";
    }

}
