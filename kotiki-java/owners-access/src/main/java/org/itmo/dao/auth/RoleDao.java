package org.itmo.dao.auth;

import org.itmo.entities.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role getByName(String name);
}
