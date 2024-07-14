package de.cwansart.unipoll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

class Result {
	private long id;
	private String name;
	private long count;
	
	public Result(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}

@Controller
public class ResultController {
	@Autowired
	private PollRepository pollRepo;
	
	@Autowired 
	private VoteRepository voteRepo;
	
	@GetMapping("/results")
	public String show(
			@RequestParam(name = "id", required = true) Long id, 
			Model model,
			HttpServletRequest request
	) {
		Optional<Poll> poll = pollRepo.findById(id);
		if (poll.isEmpty()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
		}

		List<Result> results = poll.get().getChoices().stream().map(c -> new Result(c.getId(), c.getName())).collect(Collectors.toList());
		List<Vote> votes = voteRepo.findByPollId(id);
		votes.forEach(v -> {
			v.getChoices().forEach(c -> {
				results.stream().filter(r -> c.getId().equals(r.getId()))
				                .findFirst()
				                .ifPresent(r -> r.setCount(r.getCount() + 1));
			});
		});
		
		model.addAttribute("results", results);
		model.addAttribute("id", id);
		return "result";
	}
}
