package com.example.bitirmeprojesi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/deneme")
    public String welcome(){
        return "Sayfa linkine bak";
    }
}
