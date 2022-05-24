package com.yunhalee.withEmployee.user.service;

import com.yunhalee.withEmployee.security.jwt.JwtUserDetailsService;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.dto.UserCompanyResponse;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.dto.UserTeamResponse;
import com.yunhalee.withEmployee.user.exception.DuplicatedEmailException;
import com.yunhalee.withEmployee.util.FileUploadService;

import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    public static final int USER_PER_PAGE = 9;

    private String uploadFolder;
    private UserRepository userRepository;
    private FileUploadService fileUploadService;
    private PasswordEncoder passwordEncoder;
    private JwtUserDetailsService jwtUserDetailsService;

    public UserService(@Value("${profileUpload.path") String uploadFolder,
        UserRepository userRepository,
        FileUploadService fileUploadService,
        PasswordEncoder passwordEncoder,
        JwtUserDetailsService jwtUserDetailsService) {
        this.uploadFolder = uploadFolder;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public UserResponses getAll(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, USER_PER_PAGE, Sort.by("id"));
        Page<User> pageUser = userRepository.findAllUsers(pageable);
        return UserResponses.of(pageUser.getTotalElements(),
            pageUser.getTotalPages(),
            pageUser.getContent().stream()
                .map(UserResponse::of)
                .collect(Collectors.toList()));
    }

    public UserResponse get(Integer id) {
        User user = findUserById(id);
        return userResponse(user);
    }

    @Transactional
    public Integer register(UserRequest request, MultipartFile multipartFile) {
        checkEmail(request.getEmail());
        User user = userRepository.save(request.toUser(encodePassword(request)));
        saveProfileImage(user, multipartFile);
        return user.getId();
    }

    private String saveProfileImage(User user, MultipartFile multipartFile) {
        String imageUrl = "";
        if (multipartFile != null && !multipartFile.isEmpty()) {
            imageUrl = fileUploadService.uploadProfileImage(String.valueOf(user.getId()), multipartFile);
            user.changeImageURL(imageUrl);
            userRepository.save(user);
        }
        return imageUrl;
    }


    private String encodePassword(UserRequest request) {
        return passwordEncoder.encode(request.getPassword());
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException("This email is already in use. email : " + email);
        }
    }

    @Transactional
    public UserTokenResponse update(Integer id, UserRequest request, MultipartFile multipartFile) {
        checkEmail(request.getEmail(), id);
        User user = findUserById(id);
        user.update(getUpdateUser(request));
        saveProfileImage(user, multipartFile);
        return UserTokenResponse.of(SimpleUserResponse.of(user), jwtUserDetailsService.generateToken(user));
    }

    private void checkEmail(String email, Integer id) {
        if (userRepository.existsByEmail(email) && (findUserByEmail(email).getId() != id)) {
            throw new DuplicatedEmailException("This email is already in use. email : " + email);
        }
    }

    private User getUpdateUser(UserRequest request) {
        if (!request.getPassword().equals("")) {
            return request.toUser(encodePassword(request));
        }
        return request.toUser();
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("This User doesn't exist"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("This User doesn't exist"));
    }


    public List<SimpleUserResponse> simpleUserResponses(Set<User> users) {
        return users.stream()
            .map(SimpleUserResponse::of)
            .collect(Collectors.toList());
    }

    private UserResponse userResponse(User user) {
        return UserResponse.of(user, userTeamResponses(user), userCompanyResponses(user));
    }

    private List<UserTeamResponse> userTeamResponses(User user) {
        return user.getTeams().stream()
            .map(UserTeamResponse::of)
            .collect(Collectors.toList());
    }

    private List<UserCompanyResponse> userCompanyResponses(User user) {
        return user.getCompanies().stream()
            .map(UserCompanyResponse::of)
            .collect(Collectors.toList());
    }
}
