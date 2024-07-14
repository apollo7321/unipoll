package de.cwansart.unipoll;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class DeleteController {
	@Autowired
	private AuthService auth;
	
	@Autowired
	private PollRepository pollRepo;
	
	@GetMapping("/delete") 
	public String confirm(@RequestParam(name = "id", required = true) long id, Model model) {
		if (!auth.isAuthenticated()) {
		    return "redirect:/login?p=list";
	    }
		
		if (!pollRepo.findById(id).isPresent()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll not found");
		}
		
		model.addAttribute("id", id);
		return "confirm_delete";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(name = "id", required = true) long id, Model model) {
		if (!auth.isAuthenticated()) {
	        return "redirect:/login?p=list";
      }
		
		Optional<Poll> poll = pollRepo.findById(id);
		if (!poll.isPresent()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll not found");
		}
		poll.get().setDeleted(true);
		pollRepo.save(poll.get());
		return "redirect:/list";
	}
}
