package com.cs.sigm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sigm.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
