package com.forum.gamingforumauth.repository;

import com.forum.gamingforumauth.model.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile,Long> {
    Optional<UserProfile> findByUserId(Long userId); //ovo treba proveriti
    // sluzi da se pronadje UserProfile od trenutno ulogovanog usera
}
