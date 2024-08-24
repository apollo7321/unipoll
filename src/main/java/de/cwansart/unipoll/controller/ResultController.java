package de.cwansart.unipoll.controller;

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

import de.cwansart.unipoll.entity.Poll;
import de.cwansart.unipoll.entity.Vote;
import de.cwansart.unipoll.model.Result;
import de.cwansart.unipoll.repository.PollRepository;
import de.cwansart.unipoll.repository.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ResultController {
    @Autowired
    private PollRepository pollRepo;

    @Autowired
    private VoteRepository voteRepo;

    @GetMapping("/results")
    public String show(@RequestParam(name = "id", required = true) Long id, Model model, HttpServletRequest request) {
        Optional<Poll> poll = pollRepo.findById(id);
        if (poll.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
        }

        List<Result> results = poll.get().getChoices().stream().map(c -> new Result(c.getId(), c.getName()))
                .collect(Collectors.toList());
        List<Vote> votes = voteRepo.findByPollId(id);
        votes.forEach(v -> {
            v.getChoices().forEach(c -> {
                results.stream().filter(r -> c.getId().equals(r.getId())).findFirst()
                        .ifPresent(r -> r.setCount(r.getCount() + 1));
            });
        });

        model.addAttribute("results", results);
        model.addAttribute("id", id);
        model.addAttribute("name", poll.get().getName());
        model.addAttribute("closed", poll.get().isDeleted());
        return "result";
    }
}
