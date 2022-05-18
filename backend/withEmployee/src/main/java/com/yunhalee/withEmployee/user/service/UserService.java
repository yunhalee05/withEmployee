package com.yunhalee.withEmployee.user.service;

import com.yunhalee.withEmployee.user.dto.UserCompanyResponse;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.dto.UserTeamResponse;
import com.yunhalee.withEmployee.user.exception.DuplicatedEmailException;
import com.yunhalee.withEmployee.util.FileUploadService;

import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.dto.UserDTO;
import com.yunhalee.withEmployee.user.dto.UserListByPageDTO;
import com.yunhalee.withEmployee.team.domain.Team;
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

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    public static final int USER_PER_PAGE = 9;

    private String uploadFolder;
    private UserRepository repo;
    private FileUploadService fileUploadService;
    private TeamRepository teamRepo;
    private PasswordEncoder passwordEncoder;

    public UserService(@Value("${profileUpload.path") String uploadFolder,
        UserRepository repo,
        FileUploadService fileUploadService,
        TeamRepository teamRepo,
        PasswordEncoder passwordEncoder) {
        this.uploadFolder = uploadFolder;
        this.repo = repo;
        this.fileUploadService = fileUploadService;
        this.teamRepo = teamRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponses getAll(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, USER_PER_PAGE, Sort.by("id"));
        Page<User> pageUser = repo.findAllUsers(pageable);
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
        User user = repo.save(request.toUser(encodePassword(request)));
        saveProfileImage(user, multipartFile);
        return user.getId();
    }

    private String saveProfileImage(User user, MultipartFile multipartFile) {
        String imageUrl = fileUploadService.saveProfileImage(String.valueOf(user.getId()), multipartFile);
        user.changeImageURL(imageUrl);
        repo.save(user);
        return imageUrl;
    }


    private String encodePassword(UserRequest request) {
        return passwordEncoder.encode(request.getPassword());
    }

    private void checkEmail(String email) {
        if (repo.existsByEmail(email)) {
            throw new DuplicatedEmailException("This email is already in use. email : " + email);
        }
    }

//
//    public UserDTO save(UserDTO userDTO, MultipartFile multipartFile) throws IOException {
//
//        if(userDTO.getId()!=null){
//            User existingUser = repo.findById(userDTO.getId()).get();
//            existingUser.setName(userDTO.getName());
//            if(!userDTO.getPassword().equals("")){
//                existingUser.setPassword(userDTO.getPassword());
//            }
//            if(multipartFile != null){
//                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//                try{
//                    existingUser.setImageName(fileName);
//                    String uploadDir = "profileUploads/"+ existingUser.getId();
//                    FileUploadUtils.cleanDir(uploadDir);
//                    FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
//                    existingUser.setImageUrl("/profileUploads/"+ existingUser.getId() + "/" + fileName);
//                }catch (IOException e){
//                    new IOException("Could not save file : " + multipartFile.getOriginalFilename());
//                }
//
//            }
//            existingUser.setDescription(userDTO.getDescription());
//            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
//            repo.save(existingUser);
//            return new UserDTO(existingUser);
//        }else{
//            User user = new User();
//            userDTO.setId(user.getId());
//            user.setName(userDTO.getName());
//            user.setEmail(userDTO.getEmail());
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            user.setDescription(userDTO.getDescription());
//
//            if(userDTO.getRole()!=null){
//                Role role = roleRepository.findByName("CEO");
//                user.setRole(role);
//            }else{
//                Role role = roleRepository.findByName("Member");
//                user.setRole(role);
//            }
//            repo.save(user);
//
//            if(multipartFile != null){
//                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//                user.setImageName(fileName);
//                String uploadDir = "profileUploads/"+ user.getId();
//                FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
//                user.setImageUrl("/profileUploads/"+ user.getId() + "/" + fileName);
//
//            }
//
//            repo.save(user);
//
//            return new UserDTO(user);
//
//        }
//    }

    public UserDTO addTeam(String email, Integer id) {
        User user = repo.findByEmail(email).get();
        Team team = teamRepo.findByTeamId(id);

        user.addTeam(team);
        repo.save(user);
        return new UserDTO(user);
    }

    public Integer deleteTeam(Integer userId, Integer teamId) {
        User user = repo.findById(userId).get();
        Set<Team> teams = user.getTeams().stream().filter(t -> !t.getId().equals(teamId))
            .collect(Collectors.toSet());

        System.out.println(teams);
//        user.setTeams(teams);
        repo.save(user);
        return teamId;
    }

    public boolean isEmailUnique(Integer id, String email) {
        User existingUser = repo.findByEmail(email).get();

        if (existingUser == null) {
            return true;
        }
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (existingUser != null) {
                return false;
            }
        } else {
            if (existingUser.getId() != id) {
                return false;
            }
        }

        return true;
    }

    public User findUserById(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("This User doesn't exist"));
    }

    public User findUserByEmail(String email) {
        return repo.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("This User doesn't exist"));
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
