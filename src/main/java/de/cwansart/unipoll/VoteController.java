package de.cwansart.unipoll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

class ChoiceElement {
	private int id;
	private String name;
	public ChoiceElement() {
	}
	public ChoiceElement(String name, int id) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}

class VoteForm {
	private List<String> selected;
	public VoteForm() {
	}
	public VoteForm(List<String> selected) {
		this.selected = selected;
	}
	public List<String> getSelected() {
		return selected;
	}
	public void setSelected(List<String> selected) {
		this.selected = selected;
	}
}

@Controller
public class VoteController {
	@Autowired
	private PollRepository pollRepo;
	
	@Autowired
	private VoteRepository voteRepo;
	
	@GetMapping("/vote")
	public String show(
			@RequestParam(name = "id", required = true) long id,
			Model model,
			HttpServletRequest request
	) {
		Optional<Poll> poll = pollRepo.findById(id);
		if (poll.isEmpty()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
		}
		
		String userId = request.getRemoteAddr();
		
		// check if user already voted
		if (voteRepo.findByIdAndUserId(id, userId).isPresent()) {
			return "redirect:/results?id=" + id + "&v=1";
		}
		model.addAttribute("choices", poll.get().getChoices());
		model.addAttribute("voteForm", new VoteForm());
		model.addAttribute("id", id);
		return "vote";
	}
	
	@PostMapping("/vote")
	public String save(
			@RequestParam(name = "id", required = true) long id,
			@ModelAttribute("voteForm") VoteForm voteForm, 
			Model model, 
			HttpServletRequest request
	) {
		Optional<Poll> poll = pollRepo.findById(id);
		if (poll.isEmpty()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
		}
		
		String userId = request.getRemoteAddr();
		
		// check if user has already voted
		if (voteRepo.findByIdAndUserId(id, userId).isPresent()) {
			return "redirect:/results?id=" + id + "&v=1";
		}
		
		// check if selected ids belong to the given poll
		List<UChoice> choices = new ArrayList<>();
		for(String s: voteForm.getSelected()) {
			long sLong = Long.parseLong(s);
			Optional<UChoice> choice = poll.get().getChoices().stream().filter(t -> t.getId().equals(sLong)).findFirst();
			if (choice.isPresent()) {
				choices.add(choice.get());
			} else {
				throw new ResponseStatusException(HttpStatusCode.valueOf(400), "choice does not belong to poll");				
			}
		}
		
		Vote vote = new Vote();
		vote.setPoll(poll.get());
		vote.setChoices(choices);
		vote.setUserId(userId);
		voteRepo.save(vote);
		return "redirect:/vote?id=" + id;
	}
}
