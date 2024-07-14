package de.cwansart.unipoll;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

class CreateForm {
	private String topics;
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
}

@Controller
public class CreateController {
	@Autowired
	private AuthService auth;
	
	@Autowired
	private PollRepository repo;
	
	@GetMapping("/create")
	public String create(Model model) {
		if (!auth.isAuthenticated()) {
			return "redirect:/login";
		}
		model.addAttribute("createForm", new CreateForm());
		return "create";
	}
	
	@PostMapping("/create")
	public String doCreate(@ModelAttribute("createForm") CreateForm createForm, Model model) {
		if (!auth.isAuthenticated()) {
			return "redirect:/login";
		}
		
		List<String> topics = Arrays.asList(createForm.getTopics().split(","));
		if (topics.isEmpty()) {
			model.addAttribute("error", "Topics may not be empty!");
			model.addAttribute("form", new CreateForm());
			return "create";
		}
		Poll poll = new Poll();
		poll.setChoices(topics.stream().map(t -> new Choice(t)).collect(Collectors.toList()));
		poll = repo.save(poll);
		return "redirect:/vote?id=" + poll.getId();
	}
}
