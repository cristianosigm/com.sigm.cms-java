package com.cs.sigm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cs.sigm.domain.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {

}
