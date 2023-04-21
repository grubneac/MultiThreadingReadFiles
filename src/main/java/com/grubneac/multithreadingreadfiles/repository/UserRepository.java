package com.grubneac.multithreadingreadfiles.repository;

import com.grubneac.multithreadingreadfiles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
