package ru.dmeaaxd.lab2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dmeaaxd.lab2.entity.auth.Role;
import ru.dmeaaxd.lab2.repository.RoleRepository;

import java.util.List;
import java.util.Objects;

@Component
public class OnApplicationReady {

    @Autowired
    private RoleRepository roleRepository;
    private final String[] ROLES = {"USER", "SYSTEM_ADMIN", "SHOP_ADMIN"};

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        List<Role> alreadyInitMyRoles = roleRepository.findAll();
        for (String roleName : ROLES) {
            boolean containedFlag = false;
            for (Role role : alreadyInitMyRoles) {
                if (Objects.equals(role.getName(), roleName)) {
                    containedFlag = true;
                    break;
                }
            }
            if (!containedFlag) {
                roleRepository.save(Role.builder().name(roleName).build());
            }
        }
    }
}