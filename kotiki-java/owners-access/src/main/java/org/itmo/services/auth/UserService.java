package org.itmo.services.auth;

import org.itmo.dao.auth.UserDao;
import lombok.RequiredArgsConstructor;
import org.itmo.entities.auth.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;

    public User get(String username) {
        return dao.getByUsername(username);
    }

    public void save(User user) {
        dao.save(user);
    }

    public void delete(int id) {
        dao.deleteById(id);
    }
}
