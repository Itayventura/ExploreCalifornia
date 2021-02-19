package com.itayvenutra.exploreCalifornia.repo;

import com.itayvenutra.exploreCalifornia.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}