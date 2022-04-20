package com.example.appjwtrealemailauditing.repository;

import com.example.appjwtrealemailauditing.entity.Role;
import com.example.appjwtrealemailauditing.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
