package dev.jorge.projects.auth.user.repository;

import dev.jorge.projects.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
