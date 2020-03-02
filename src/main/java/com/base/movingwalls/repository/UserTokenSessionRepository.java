package com.base.movingwalls.repository;

import com.base.movingwalls.model.user.UserTokenSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenSessionRepository extends CrudRepository<UserTokenSession, Long> {

    /**
     * Find {@link UserTokenSession} for the given username.
     *
     * @param username
     * @return @{@link UserTokenSession}
     */
    Optional<UserTokenSession> findOneByUsername(String username);

}