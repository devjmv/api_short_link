package dev.shortlink.access;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AccessController {
    
    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable String id) {
        return "access " + id;
    }
}