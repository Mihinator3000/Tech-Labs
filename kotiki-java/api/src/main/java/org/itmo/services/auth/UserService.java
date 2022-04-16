package org.itmo.services.auth;

import lombok.RequiredArgsConstructor;
import org.itmo.dao.auth.UserDao;
import org.itmo.models.auth.Role;
import org.itmo.models.auth.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao dao;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.getByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User " + username + " was not found");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public void save(User user) {
        dao.save(user);
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public boolean isCurrentUserNotAnAdmin() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public int getCurrentOwnerId() {
        return dao
                .getByUsername(getCurrentUsername())
                .getOwner()
                .getId();
    }

    public int getCurrentId() {
        return dao.getByUsername(getCurrentUsername()).getId();
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
}
