package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser , Long> {
    ApplicationUser findByUsername(String username);
}
