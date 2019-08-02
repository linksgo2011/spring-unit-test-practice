package com.oocl.web.controller;

import com.oocl.web.sampleWebApp.model.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Controller
public class EmailController {

    @PostMapping("/emails")
    public ResponseEntity<Email> createEmail(@RequestBody Email email) {
        if (null == email.getTo()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/emails/" + System.currentTimeMillis())).body(email);
    }
}
