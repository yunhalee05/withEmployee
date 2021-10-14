package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.dto.UserListByPageDTO;
import com.yunhalee.withEmployee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void register() throws IOException {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("yunha");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("123456");
        userDTO.setDescription("test");
        userDTO.setPhoneNumber("01029290102");
        MultipartFile multipartFile = new MockMultipartFile("uploaded-file",
                                             "sampleFile.txt",
                                                "text/plain",
                                                            "This is the file content".getBytes());
        //when
        UserDTO userDTO1 = userService.save(userDTO, multipartFile);

        //then
        assertEquals(userDTO1.getName(), userRepository.findByEmail(userDTO1.getEmail()).getName());
        assertEquals(userDTO1.getEmail(), userRepository.findByEmail(userDTO1.getEmail()).getEmail());
        assertEquals(userDTO1.getPassword(), userRepository.findByEmail(userDTO1.getEmail()).getPassword());
        assertEquals(userDTO1.getDescription(), userRepository.findByEmail(userDTO1.getEmail()).getDescription());
        assertEquals(userDTO1.getPhoneNumber(), userRepository.findByEmail(userDTO1.getEmail()).getPhoneNumber());
        assertEquals("Member", userRepository.findByEmail(userDTO1.getEmail()).getRole().getName());
        assertEquals(userDTO1.getImageUrl(), userRepository.findByEmail(userDTO1.getEmail()).getImageUrl());

    }

    @Test
    public void login(){
        //given
        String email = "test@example.com";
        String password = "123456";

        //when
        UserDTO userDTO = userService.getByEmail(email);

        //then
        boolean match = passwordEncoder.matches(password, userDTO.getPassword());
        assertEquals(match, true);
    }

    @Test
    public void isEmailUnique(){
        //given
        String email = "test@example.com";

        //when
        boolean check = userService.isEmailUnique(null, email);

        //then
        assertEquals(false, check);
    }

    @Test
    public void updateUser() throws IOException {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("yunha");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("123456");
        userDTO.setDescription("test");
        userDTO.setPhoneNumber("01029290102");
        MultipartFile multipartFile = new MockMultipartFile("uploaded-file",
                "sample.txt",
                "text/plain",
                "This is the file content".getBytes());
        UserDTO userDTO1 = userService.save(userDTO, multipartFile);
        String originalImageUrl = userDTO1.getImageUrl();

        MultipartFile multipartFile1 = new MockMultipartFile("uploaded-file",
                "update.txt",
                "text/plain",
                "This is the file content".getBytes());

        //when
        UserDTO userDTO2 = userService.save(userDTO, multipartFile1);

        //then
        assertNotEquals(originalImageUrl, userDTO2.getImageUrl());
    }

    @Test
    public void getUserById(){
        //given
        Integer id = 17;

        //when
        UserDTO userDTO = userService.get(id);

        //then
        assertEquals(id, userDTO.getId());
    }

    @Test
    public void getUserList(){
        //given
        Integer page = 1;

        //when
        UserListByPageDTO userListByPageDTO = userService.listAll(page);

        //then
        assertEquals(9, userListByPageDTO.getUsers().size());
        System.out.println(userListByPageDTO.getUsers());
    }

    @Test
    public void addTeam(){
        //given
        String email = "test@example.com";
        Integer teamId = 1;

        //when
        UserDTO userDTO = userService.addTeam(email, teamId);

        //then
        assertNotEquals(userDTO.getTeams().size(),0);
    }

    @Test
    public void deleteTeam(){
        //given
        Integer userId = 17;
        Integer teamId = 1;

        //when
        Integer team = userService.deleteTeam(userId, teamId);

        //then
        assertEquals(0, userRepository.findById(userId).get().getTeams().size());
    }

}
