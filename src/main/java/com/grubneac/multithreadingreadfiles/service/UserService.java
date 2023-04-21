package com.grubneac.multithreadingreadfiles.service;

import com.grubneac.multithreadingreadfiles.entity.User;
import com.grubneac.multithreadingreadfiles.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository repository;

    @Async
    public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        logger.info("saving list of users {}", users.size(), " " +Thread.currentThread().getName());
        repository.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public  CompletableFuture<List<User>> findAllUsers(){
        logger.info("get list of user by " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(repository.findAll());
    }

    private List<User> parseCSVFile(MultipartFile file) throws Exception {
        List<User> users = new ArrayList<>();

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }

    }
}
