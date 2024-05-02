package com.example.controller;

import com.example.service.UserService;
import com.example.entity.User;
// import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired
    //  private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Add model attributes if needed
        model.addAttribute("user", new User());
        return "register"; 
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam("username") String username,
                                           @RequestParam("password") String password,
                                           @RequestParam("email") String email) {
    try {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        
        userService.saveUser(user); // Delegate user registration logic to UserService
        
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

//     @GetMapping("/user_list")
//     public String showUserList(Model model) {
//        System.out.println("Hi");
//         // Retrieve all users from the database
//         List<User> users = userRepository.findAll();
//
//         // Add Sthe list of users to the model
//         model.addAttribute("users", users);
//
//         // Return the name of the HTML template to render
//         return "user_list"; // Assuming you have a users.html Thymeleaf template
//     }
    
    @GetMapping("/edit/{userId}")
    public String showEditForm(@PathVariable("userId") int userId, Model model) {
        // Retrieve user details by userId and pass them to the view
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        return "edit";
    }

    @PostMapping("/edit/{userId}")
    public String editUser(@ModelAttribute User user, @PathVariable int userId) {
        // System.out.println("hi");
        user.setId(userId); // Set the user ID from the path variable
        System.out.println(user);
        // Update user details in the database
        userService.updateUser(user);
        return "redirect:/edit/{userId}";
    }

    @DeleteMapping("user_list/delete/{userId}") // Specify the correct path for deleting a user by ID
    public ResponseEntity<String> deleteUser(@PathVariable  int userId) {
        try {
            // Call the service method to delete the user
            System.out.println("hi");
            userService.deleteUserById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("User not found with ID: " + userId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Search Function
    @GetMapping("/user_list")
    public String showUserList(Model model, @RequestParam(required = false) String keyword) {
        List<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            // If a keyword is provided, search for users by username or email containing the keyword
            users = userService.searchUsers(keyword);
        } else {
            // If no keyword is provided, retrieve all users
            users = userService.getAllUsers();
        }

        // Add the list of users to the model
        model.addAttribute("users", users);

        // Pass the keyword to the view for displaying in the search input field
        model.addAttribute("keyword", keyword);

        // Return the name of the HTML template to render
        return "user_list";
    }

    @GetMapping("/user_list/paginated")
    @ResponseBody
    public ResponseEntity<?> getUsersPage(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            int totalUsers = userService.getAllUsers().size(); // Total number of users
            int totalPages = (int) Math.ceil((double) totalUsers / size); // Calculate total pages
            Page<User> usersPage = userService.getUsers(PageRequest.of(page, size));
            
            // Construct JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("users", usersPage.getContent());
            response.put("currentPage", page);
            response.put("totalPages", totalPages);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch users");
        }
    }




}


 