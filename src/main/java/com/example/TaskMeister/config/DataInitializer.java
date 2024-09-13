package com.example.TaskMeister.config;

import com.example.TaskMeister.model.Role;
import com.example.TaskMeister.model.ERole;
import com.example.TaskMeister.repositories.IRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(IRoleRepository iRoleRepository) {
        return args -> {
            // Insert USER role if not present
            if (iRoleRepository.findByName(ERole.USER).isEmpty()) {
                iRoleRepository.save(new Role(ERole.USER));
            }
            // Insert MANAGER role if not present
            if (iRoleRepository.findByName(ERole.MANAGER).isEmpty()) {
                iRoleRepository.save(new Role(ERole.MANAGER));
            }
            // Insert ADMIN role if not present
            if (iRoleRepository.findByName(ERole.ADMIN).isEmpty()) {
                iRoleRepository.save(new Role(ERole.ADMIN));
            }
        };
    }
}
