package de.cwansart.unipoll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

class LoginForm {
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

@Controller
public class LoginController {
	@Autowired
	private AuthService auth;
	
	@GetMapping("/login")
	public String login(Model model) {
		if (auth.isAuthenticated()) {
			return "redirect:/create";
		}
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		if (!auth.login(loginForm.getPassword())) {
			model.addAttribute("error", "Invalid password!");
		} else {
			return "redirect:/create";
		}
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
}
