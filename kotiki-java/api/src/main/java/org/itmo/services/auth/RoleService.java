package org.itmo.services.auth;

import lombok.RequiredArgsConstructor;
import org.itmo.dao.auth.RoleDao;
import org.itmo.models.auth.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    public Role getByName(String name) {
        return roleDao.getByName(name);
    }
}
