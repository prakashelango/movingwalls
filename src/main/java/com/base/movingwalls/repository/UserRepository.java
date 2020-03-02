package com.base.movingwalls.repository;

import com.base.movingwalls.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param username
     * @return @{@link User}
     */
    Optional<User> findOneByUsername(String username);

    /**
     * @param username
     * @return @{@link User}
     */
    Optional<User> findOneByUsernameAndPassword(String username, String password);
}
