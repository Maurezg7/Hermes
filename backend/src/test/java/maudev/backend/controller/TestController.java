package maudev.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "¡API funcionando! - " + new java.util.Date();
    }

    @PostMapping("/hello")
    public String hello(@RequestBody String name) {
        return "Hola " + name + "!";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id) {
        return "Usuario ID: " + id;
    }
}