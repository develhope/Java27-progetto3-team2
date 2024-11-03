package com.develhope.Java27_progetto3_team2.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    //The accessibility is set to User just to show the functionality
    @PatchMapping("/user/role")
    public ResponseEntity<UserDTO> changeUserRole(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String role){
        UserDTO userDTO = userService.changeUserRole(userDetails,role);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long id) {
        UserDTO userDTO = userService.getUserDTOById(id);
        log.debug("User with id:{} found", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user){
        UserDTO userDTO = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PatchMapping("/user/{userId}/name")
    public ResponseEntity<UserDTO> changeUserName(@PathVariable("userId") Long id, @RequestParam String name) {
        UserDTO userDTO = userService.changeUserName(id, name);
        log.debug("Changed name of user with id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PatchMapping("/user/{userId}/surname")
    public ResponseEntity<UserDTO> changeUserSurname(@PathVariable("userId") Long id, @RequestParam String surname) {
        UserDTO userDTO = userService.changerUserSurname(id, surname);
        log.debug("Changed surname of user with id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);

    }
}
