package com.develhope.Java27_progetto3_team2.usertest;

import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserDTO;
import com.develhope.Java27_progetto3_team2.user.UserMapper;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import com.develhope.Java27_progetto3_team2.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;
    private UserDTO changedUserDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Mario");
        user.setSurname("Brigida");

        userDTO = new UserDTO();
        userDTO.setName("Mario");
        userDTO.setSurname("Brigida");

        changedUserDTO = new UserDTO();
        changedUserDTO.setName("Luigi");
        changedUserDTO.setSurname("Bianco");
    }

    @Test
    void getAllUsers() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> usersPage = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        Page<UserDTO> result = userService.getAllUsers(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Mario", result.getContent().get(0).getName());

        verify(userRepository).findAll(pageRequest);
        verify(userMapper).toDTO(any(User.class));
    }

    @Test
    void getUserDTOById() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertEquals("Mario", result.getName());
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(user);
    }

    @Test
    void changeUserName() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(changedUserDTO);

        UserDTO result = userService.changeUserName(1L, "Luigi");

        assertEquals("Luigi", result.getName());
        verify(userRepository).save(user);
    }

    @Test
    void changerUserSurname() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(changedUserDTO);

        UserDTO result = userService.changerUserSurname(1L, "Bianco");

        assertEquals("Bianco", result.getSurname());
        verify(userRepository).save(user);
    }
}