package com.yunhalee.withEmployee.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.exception.DuplicatedEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserServiceTest extends MockBeans {

    private static final String DUPLICATED_EMAIL_EXCEPTION = "This email is already in use.";

    private final String TEST_UPLOAD_FOLDER = "testUploadFolder";
    private final Integer ID = 1;
    private final String NAME = "testUser";
    private final String EMAIL = "test@example.com";
    private final String PASSWORD = "123456";
    private final String IMAGE_URL = "testUploadFolder/testImage.png";
    private final String DESCRIPTION = "testDescription";
    private final String PHONE_NUMBER = "01000000000";
    private final boolean IS_CEO = false;
    private final MultipartFile MULTIPART_FILE = new MockMultipartFile("test_image",
        "test_image",
        "image/png",
        new byte[10]);


    @InjectMocks
    private UserService userService = new UserService(TEST_UPLOAD_FOLDER, userRepository, fileUploadService, teamRepository, passwordEncoder);

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
        when(fileUploadService.saveProfileImage(any(), any())).thenReturn(IMAGE_URL);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
        UserResponse response = userService.register(request, MULTIPART_FILE);

        //then
        checkEquals(response, user);
        assertThat(response.getRole()).isEqualTo(Role.MEMBER.name());
    }

    @Test
    void register_with_already_exists_email_is_invalid(){
        // when
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertThatThrownBy(() -> userService.register(request, MULTIPART_FILE))
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

}