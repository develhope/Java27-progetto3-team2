package com.develhope.Java27_progetto3_team2.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam int pageSize, @RequestParam int pageItemQuantity) {
        List<UserDTO> userDTOList = userService.getAllUsers(pageSize, pageItemQuantity).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
    }

    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            log.debug("User with id:{} found", id);
            return ResponseEntity.status(HttpStatus.FOUND).body(userDTO);
        } catch (Exception e) {
            log.error("User with id:{} not found", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            UserDTO userDTO = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PatchMapping("/user/{userId}/name")
    public ResponseEntity<?> changeUserName(@PathVariable("userId") Long id, @RequestParam String name) {
        try {
            UserDTO userDTO = userService.changeUserName(id, name);
            log.debug("Changed name of user with id {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (Exception e){
            log.error("User with id:{} not found", e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/user/{userId}/surname")
    public ResponseEntity<?> changeUserSurname(@PathVariable("userId") Long id, @RequestParam String surname) {
        try {
            UserDTO userDTO = userService.changerUserSurname(id, surname);
            log.debug("Changed surname of user with id {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (Exception e){
            log.error("User with id:{} not found", e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
