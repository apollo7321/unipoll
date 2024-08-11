package de.cwansart.unipoll.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthService {
    private static final String passwordHash = "$2a$10$r9rH7cW8tIOJnzPX81SyOOTlAM0xNMPUf/Jvqr2IMEHpyuqN.lj8y";

    private boolean isAuthenticated = false;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean login(String password) {
        isAuthenticated = BCrypt.checkpw(password, passwordHash);
        return this.isAuthenticated;
    }
}
