package com.yunhalee.withEmployee.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.exception.DuplicatedEmailException;
import com.yunhalee.withEmployee.user.exception.UserNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class UserServiceTest extends MockBeans{

    private static final String DUPLICATED_EMAIL_EXCEPTION = "This email is already in use.";
    private static final String USER_NOT_FOUND_EXCEPTION = "This User doesn't exist";

    private final String TEST_UPLOAD_FOLDER = "testUploadFolder";
    private final Integer ID = 1;
    private final String NAME = "testUser";
    private final String EMAIL = "test@example.com";
    private final String PASSWORD = "123456";
    private final String IMAGE_URL = "/testUploadFolder/testImage.png";
    private final String DESCRIPTION = "testDescription";
    private final String PHONE_NUMBER = "01000000000";
    private final boolean IS_CEO = false;
    private final MultipartFile MULTIPART_FILE = new MockMultipartFile("test_image",
        "test_image",
        "image/png",
        new byte[10]);

    @InjectMocks
    private UserService userService = new UserService(TEST_UPLOAD_FOLDER,
        userRepository,
        fileUploadService,
        passwordEncoder,
        jwtUserDetailsService);

    private UserRequest request;
    private User user;

    @BeforeEach
    void setUp() {
        request = new UserRequest(NAME,
            EMAIL,
            PASSWORD,
            DESCRIPTION,
            PHONE_NUMBER,
            IS_CEO);

        user = User.builder()
            .id(ID)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .imageUrl(IMAGE_URL)
            .description(DESCRIPTION)
            .phoneNumber(PHONE_NUMBER)
            .role(Role.MEMBER).build();
    }

    @Test
    void register_user() {
        // when
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        when(fileUploadService.uploadProfileImage(any(), any())).thenReturn(IMAGE_URL);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        Integer id = userService.register(request, MULTIPART_FILE);

        // then
        assertThat(id).isEqualTo(user.getId());
    }

    @Test
    void register_with_already_exists_email_is_invalid() {
        // when
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertThatThrownBy(() -> userService.register(request, MULTIPART_FILE))
            .isInstanceOf(DuplicatedEmailException.class)
            .hasMessageContaining(DUPLICATED_EMAIL_EXCEPTION);
    }

    @Test
    void get_user_by_id() {
        // when
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        UserResponse response = userService.get(ID);

        // then
        checkEquals(response, user);
    }

    @Test
    void get_user_by_not_existing_id_is_invalid() {
        // when
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.get(ID))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage(USER_NOT_FOUND_EXCEPTION);
    }

    @Test
    void get_user_list() {
        // given
        Integer page = 1;
        List<User> expectedUsers = Arrays.asList(UserTest.MEMBER, UserTest.CEO, UserTest.LEADER, UserTest.ADMIN);

        // when
        doReturn(new PageImpl<>(expectedUsers)).when(userRepository).findAllUsers(any());
        UserResponses responses = userService.getAll(page);

        //then
        assertThat(responses.getTotalElement()).isEqualTo(4);
        assertThat(responses.getTotalPage()).isEqualTo(1);
        assertThat(responses.getUsers().size()).isEqualTo(4);
        checkEquals(responses, expectedUsers);
    }

    @Test
    void update_user() {
        // given
        UserRequest updateRequest = new UserRequest(UserTest.MEMBER.getName(),
            UserTest.MEMBER.getEmail(),
            UserTest.MEMBER.getPassword(),
            UserTest.MEMBER.getDescription(),
            UserTest.MEMBER.getPhoneNumber(),
            false);

        // when
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(fileUploadService.uploadProfileImage(any(), any())).thenReturn(UserTest.MEMBER.getImageUrl());
        when(passwordEncoder.encode(PASSWORD)).thenReturn(UserTest.MEMBER.getPassword());
        when(jwtUserDetailsService.generateToken(any())).thenReturn("token");
        UserTokenResponse response = userService.update(ID, updateRequest, MULTIPART_FILE);

        // then
        checkEquals(response.getUser(), UserTest.MEMBER);
    }

    @Test
    void update_user_with_already_in_use_email_is_invalid() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(UserTest.CEO));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userService.update(1, request, MULTIPART_FILE))
            .isInstanceOf(DuplicatedEmailException.class)
            .hasMessageContaining(DUPLICATED_EMAIL_EXCEPTION);
    }

    private void checkEquals(UserResponse response, User user) {
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getName()).isEqualTo(user.getName());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getImageUrl()).isEqualTo(user.getImageUrl());
        assertThat(response.getDescription()).isEqualTo(user.getDescription());
        assertThat(response.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
    }

    private void checkEquals(SimpleUserResponse response, User user) {
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getName()).isEqualTo(user.getName());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getImageUrl()).isEqualTo(user.getImageUrl());
        assertThat(response.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
    }

    private void checkEquals(UserResponses responses, List<User> expectedUsers) {
        List<UserResponse> users = responses.getUsers();
        for (int i = 0; i < users.size(); i++) {
            checkEquals(users.get(i), expectedUsers.get(i));
        }
    }

}