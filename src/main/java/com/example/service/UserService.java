package com.example.service;

import java.util.List;
import com.example.repository.UserRepository;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user.getId(),user.getUsername(),user.getEmail(),user.getPassword());
    }

    // Method to delete user by ID
    public void deleteUserById(int id) {
        // Check if the user exists
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Delete the user by ID
            userRepository.deleteById(id);
        } else {
            // Handle the case where the user does not exist
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    //Search
    public List<User> searchUsers(String keyword) {
        // Call the repository method to search for users by username or email containing the keyword
        return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    //Pagination
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
