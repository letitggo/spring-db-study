package com.example.redis.hello;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        session.setAttribute("name", name);
        return "saved";
    }

    @GetMapping("/myName")
    public String myName(HttpSession session) {
        String myName = session.getAttribute("name").toString();
        return myName;
    }
}
