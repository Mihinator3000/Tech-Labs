package org.itmo.services.auth;

import lombok.RequiredArgsConstructor;
import org.itmo.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
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

import static org.itmo.configs.OwnersRabbitConfig.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final RabbitTemplate template;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = get(username);

        if (user == null)
            throw new UsernameNotFoundException("User " + username + " was not found");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public void save(UserDto user) {
        template.convertAndSend(
                OWNERS_EXCHANGE,
                USERS_SAVE_ROUTING_KEY,
                user);
    }

    public void delete(int id) {
        template.convertAndSend(
                OWNERS_EXCHANGE,
                USERS_DELETE_ROUTING_KEY,
                id);
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
        return get(getCurrentUsername())
                .getOwnerId();
    }

    public int getCurrentId() {
        return get(getCurrentUsername()).getId();
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

    private UserDto get(String username) {
        return template.convertSendAndReceiveAsType(
                OWNERS_EXCHANGE,
                USERS_GET_ROUTING_KEY,
                username,
                ParameterizedTypeReference.forType(UserDto.class));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
