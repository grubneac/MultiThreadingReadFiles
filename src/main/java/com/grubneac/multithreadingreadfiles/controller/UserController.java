package com.grubneac.multithreadingreadfiles.controller;

import com.grubneac.multithreadingreadfiles.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping(value = "/users", consumes = {MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUser(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            service.saveUser(file);
        }
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return service.findAllUsers().thenApply(ResponseEntity::ok);
    }
}
