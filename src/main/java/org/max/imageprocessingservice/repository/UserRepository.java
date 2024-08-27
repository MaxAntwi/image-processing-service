package org.max.imageprocessingservice.repository;

import org.max.imageprocessingservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
