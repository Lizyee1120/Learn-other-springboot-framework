package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="update user_acc set username = ?2, email = ?3 , password = ?4 WHERE id = ?1", nativeQuery=true)
    public void updateUser(Integer id, String username, String email, String password);
    
    // Method to delete user by ID
    public void deleteById(int id);

    //Search
    public List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);
    
     // Method for pagination
     public Page<User> findAll(Pageable pageable);
}
