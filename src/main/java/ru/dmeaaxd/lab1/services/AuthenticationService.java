package ru.dmeaaxd.lab1.services;

import ru.dmeaaxd.lab1.entity.controllers.auth.*;

public interface AuthenticationService {

    AuthenticationResponse clientRegister(ClientRegisterRequest request);
    AuthenticationResponse auth(AuthenticationRequest request);
}
