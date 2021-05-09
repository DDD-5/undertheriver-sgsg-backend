package com.undertheriver.sgsg;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Void> health() {
        return ResponseEntity.noContent()
            .build();
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity
            .ok("Hello World!");
    }
}
