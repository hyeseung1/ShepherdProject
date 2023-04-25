package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.vo.request.CreateUserPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.sheperdmoney.interviewproject.repository.*;

@RestController
public class UserController {
    // created a userRepository instance
    private final UserRepository userRepository;

    // TODO: wire in the user repository (~ 1 line)
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PutMapping("/user")
    public ResponseEntity<Integer> createUser(@RequestBody CreateUserPayload payload) {
        // TODO: Create an user entity with information given in the payload, store it in the database
        //       and return the id of the user in 200 OK response
        return ResponseEntity.ok(userRepository.createUser(payload));
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam int userId) {
        // TODO: Return 200 OK if a user with the given ID exists, and the deletion is successful
        //       Return 400 Bad Request if a user with the ID does not exist
        //       The response body could be anything you consider appropriate
        boolean userDeleted = userRepository.deleteUser(userId);
        if (userDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
