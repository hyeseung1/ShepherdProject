package com.shepherdmoney.interviewproject.repository;

import com.shepherdmoney.interviewproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Crud Repository to store User classes
 */
@Repository("UserRepo")
public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean deleteByUserId(Integer userId);
    public ResponseEntity<Integer> createUser(CreateUserPayload payload);
    public boolean deleteUser(Integer userId);
}
