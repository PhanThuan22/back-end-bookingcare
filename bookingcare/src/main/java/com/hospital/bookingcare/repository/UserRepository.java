package com.hospital.bookingcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.bookingcare.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

    void deleteById(Long id);

    User findByEmail(String email);
    
    List<User> findTop10ByRoleId(String roleId);
    List<User> findByRoleId(String roleId);
    Optional<User> findById(Long id);
    
    


}
