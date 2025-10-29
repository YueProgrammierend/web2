package com.example.guestbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index"; // src/main/resources/templates/index.html
    }
    @GetMapping("/guestbook")
    public String guestbook() {
        return "guestbook";
    }
}