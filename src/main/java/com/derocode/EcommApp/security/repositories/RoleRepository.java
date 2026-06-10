package com.derocode.EcommApp.security.repositories;



import com.derocode.EcommApp.security.models.Role;
import com.derocode.EcommApp.security.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByName(RoleEnum name);
}
