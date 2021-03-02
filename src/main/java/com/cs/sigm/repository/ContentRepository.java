package com.cs.sigm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sigm.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {

}
