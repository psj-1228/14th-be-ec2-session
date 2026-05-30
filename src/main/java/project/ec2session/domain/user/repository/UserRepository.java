package project.ec2session.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ec2session.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
