package org.itmo.services.auth;

import org.itmo.dao.auth.RoleDao;
import org.itmo.models.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getByName(String name) {
        return roleDao.getByName(name);
    }
}
