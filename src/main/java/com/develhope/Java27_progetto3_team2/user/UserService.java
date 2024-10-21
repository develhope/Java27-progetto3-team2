package com.develhope.Java27_progetto3_team2.user;

import com.develhope.Java27_progetto3_team2.user.utils.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;

    public Page<UserDTO> getAllUsers(int page, int quantity){
        Pageable pageable = PageRequest.of(page,quantity);
        Page<User> userDTOPage = userRepository.findAll(pageable);
        return userDTOPage.map(userMapper::toDTO);
    }

    public UserDTO getUserById(Long id)throws Exception{
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with id: " + id + " not found!"));
        return userMapper.toDTO(user);
    }

    public UserDTO changeUserName(Long id, String name)throws Exception{
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with id: " + id + " not found!"));
        user.setName(name);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO changerUserSurname(Long id, String surname) throws Exception{
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User with id: " + id + " not found!"));
        user.setSurname(surname);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO registerUser(CreateUserDTO createUserDTO) throws Exception{
        if(userRepository.existsByEmail(createUserDTO.getEmail()) || userRepository.existsByUsername(createUserDTO.getUsername())){
            throw new Exception("User already exists");
        }
        User user = new User(createUserDTO.getId(), createUserDTO.getName(), createUserDTO.getSurname(), createUserDTO.getUsername(), passwordService.passwordEncoder().encode(createUserDTO.getPassword()), createUserDTO.getEmail(),createUserDTO.getRole());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

}
