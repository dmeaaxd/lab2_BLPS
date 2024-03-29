package ru.dmeaaxd.lab1.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab1.entity.User;
import ru.dmeaaxd.lab1.entity.controllers.auth.AuthenticationRequest;
import ru.dmeaaxd.lab1.entity.controllers.auth.AuthenticationResponse;
import ru.dmeaaxd.lab1.entity.controllers.auth.ClientRegisterRequest;
import ru.dmeaaxd.lab1.repository.UserRepository;
import ru.dmeaaxd.lab1.services.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;

    @Override
    @Transactional
    public AuthenticationResponse clientRegister(ClientRegisterRequest request) {
        var user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setAccountNumber(String.valueOf(request.account_number()));


        userRepository.save(user);

        return new AuthenticationResponse(Math.toIntExact(user.getUserId()));
    }

    @Override
    public AuthenticationResponse auth(AuthenticationRequest request) {
        return null;
    }
}
