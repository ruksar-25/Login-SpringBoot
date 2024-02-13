package com.example.LoginTask.service;

import com.example.LoginTask.models.User;
import com.example.LoginTask.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User loginUser(User user) {
        User userData=getUserByName(user.getName());
        if(userData==null) {
            throw new RuntimeException("No user data found");
        }
        if(!userData.getPassword().equals(user.getPassword())){
            throw new RuntimeException("Incorrect Password");
        }
        return userData;
    }

    private User getUserByName(String name){
        List<User> userData=userRepository.getUserByName(name);
        return userData.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    public UserDetails getUserDetailsByName(String name){
        User user = getUserByName(name);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName()).password(user.getPassword()).build();
    }

    public User registerUser(User user) {
        User userData=getUserByName(user.getName());
        if(userData != null){
            if(userData.getPassword().equals(user.getPassword())) {
                user.setId(userData.getId());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
            else {
                throw new RuntimeException("This user already exists but password is incorrect");
            }
        }
        return userRepository.save(user);
    }
    public User getUser(Integer id){
        return userRepository.getByUserId(id);
    }
}
