package de.cwansart.unipoll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
	public String login(@RequestParam(name = "p", required = true) String page, Model model) {
		if (auth.isAuthenticated()) {
			return "redirect:/" + page;
		}
		model.addAttribute("loginForm", new LoginForm());
		model.addAttribute("page", page);
		return "login";
	}
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute("loginForm") LoginForm loginForm, @RequestParam(name = "p", required = true) String page, Model model) {
		if (!auth.login(loginForm.getPassword())) {
			model.addAttribute("error", "Invalid password!");
			model.addAttribute("loginForm", new LoginForm());
			model.addAttribute("page", page);
			return "login";
		} else {
			if (!page.equals("create") && !page.equals("list")) {
				throw new ResponseStatusException(HttpStatusCode.valueOf(400), "unknown redirect page");
			}
			return "redirect:/" + page;
		}
	}
}
