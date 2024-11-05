package com.develhope.Java27_progetto3_team2.user;

import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
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


    public UserDTO getUserDTOById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!"));
        return userMapper.toDTO(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found!"));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found!"));
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

    public boolean addRestaurantToUser(UserDetails userDetails, Restaurant restaurant){
        User user = (User) userDetails;
        if(restaurant != null && user.getRole() == Role.ROLE_MANAGER){
            user.setRestaurant(restaurant);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserDTO changeUserRole(UserDetails userDetails,String role){
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("User with email: " + userDetails.getUsername() + " nout found!"));
        user.setRole(Role.valueOf(role.toUpperCase()));
        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
