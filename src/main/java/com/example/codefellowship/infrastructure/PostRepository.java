package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
