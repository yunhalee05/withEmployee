package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.RoleRepository;
import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import com.yunhalee.withEmployee.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll(){
       return repo.findAllUsers();
    }

    public UserDTO get(Integer id) throws UserNotFoundException {
        try {
            User user = repo.findById(id).get();
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("This User doesn't exist");
        }

    }

    public UserDTO getByEmail(String email) throws UserNotFoundException{
        try{
            User user = repo.findByEmail(email);
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("This User doesn't exist");
        }
    }

    public UserDTO save(UserDTO userDTO) {

        if(userDTO.getId()!=null){
            User existingUser = repo.findById(userDTO.getId()).get();
            existingUser.setName(userDTO.getName());
            if(userDTO.getPassword()!=null){
                existingUser.setPassword(userDTO.getPassword());
            }
            existingUser.setImageUrl(userDTO.getImageUrl());
            existingUser.setDescription(userDTO.getDescription());
            repo.save(existingUser);
            return new UserDTO(existingUser);
        }else{
            User user = new User();
            userDTO.setId(user.getId());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setImageUrl(userDTO.getImageUrl());
            user.setDescription(userDTO.getDescription());

            if(userDTO.getRole()!=null){
                Role role = roleRepository.findByName("CEO");
                user.setRole(role);
            }else{
                Role role = roleRepository.findByName("Member");
                user.setRole(role);
            }
            repo.save(user);
            return new UserDTO(user);

        }
    }

    public UserDTO addTeam(String email, Integer id){
        User user = repo.findByEmail(email);
        Team team = teamRepo.findByTeamId(id);

        user.addTeam(team);
        repo.save(user);
        return new UserDTO(user);
    }

    public Integer deleteTeam(Integer userId, Integer teamId){
        User user = repo.findById(userId).get();
        Set<Team> teams = user.getTeams().stream().filter(t->!t.getId().equals(teamId)).collect(Collectors.toSet());

        System.out.println(teams);
        user.setTeams(teams);
        repo.save(user);
        return teamId;
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email){
        User existingUser = repo.findByEmail(email);

        if(existingUser==null){
            return true;
        }
        boolean isCreatingNew = (id ==null);
        if(isCreatingNew){
            if(existingUser !=null)return false;
        }else{
            if(existingUser.getId() != id) return false;
        }

        return true;
    }
}
