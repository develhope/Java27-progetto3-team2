package com.develhope.Java27_progetto3_team2.user;

import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserDTO> getAllUsers(int page, int quantity){
        Pageable pageable = PageRequest.of(page,quantity);
        Page<User> userDTOPage = userRepository.findAll(pageable);
        return userDTOPage.map(userMapper::toDTO);
    }

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!"));
        return userMapper.toDTO(user);
    }

    public UserDTO changeUserName(Long id, String name){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!"));
        user.setName(name);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO changerUserSurname(Long id, String surname) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!"));
        user.setSurname(surname);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO addUser(User user) {
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO changeUserRole(UserDetails userDetails,String role){
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("User with email: " + userDetails.getUsername() + " nout found!"));
        user.setRole(Role.valueOf(role.toUpperCase()));
        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
