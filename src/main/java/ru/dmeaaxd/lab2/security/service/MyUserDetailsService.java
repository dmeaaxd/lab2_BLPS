package ru.dmeaaxd.lab2.security.service;


import ru.dmeaaxd.lab2.security.userdetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.repository.ClientRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return new MyUserDetails(client);
    }
}
