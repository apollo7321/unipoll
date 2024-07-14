package de.cwansart.unipoll;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthService {
	private final String storedPassword = "$2a$12$2R9Zcwr8gaslSNqV1ee6K.qDZutsnRkRaGaEeRkbBzYtDumFtCJdK";
	private boolean isAuthenticated = false;
	
	public boolean isAuthenticated() {
//		return isAuthenticated;
		return true;
	}
	
	public boolean login(String password) {
//		isAuthenticated = BCrypt.checkpw(password, storedPassword);
//		return this.isAuthenticated;
		isAuthenticated = true;
		return isAuthenticated;
	}
}
