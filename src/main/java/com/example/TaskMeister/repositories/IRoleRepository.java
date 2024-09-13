package com.example.TaskMeister.repositories;

import com.example.TaskMeister.model.Role;
import com.example.TaskMeister.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
