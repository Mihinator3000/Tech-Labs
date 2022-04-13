package org.itmo.dao.auth;

import org.itmo.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User getByUsername(String username);
}
