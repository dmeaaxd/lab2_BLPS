package ru.dmeaaxd.lab2.security.jaas;

import lombok.Setter;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.dmeaaxd.lab2.entity.auth.Role;
import ru.dmeaaxd.lab2.repository.ClientRepository;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Setter
public class MyAuthorityGranter implements AuthorityGranter {
    private ClientRepository userRepository;
    @Override
    public Set<String> grant(Principal principal) {
        Set<String> stringRoles = new HashSet<>();
        for (Role role : userRepository.findByUsername(principal.getName()).getRoles()){
            stringRoles.add(role.getName());
        }
        return stringRoles;
    }
}
