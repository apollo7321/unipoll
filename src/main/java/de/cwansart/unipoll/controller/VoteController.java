package de.cwansart.unipoll.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import de.cwansart.unipoll.entity.Choice;
import de.cwansart.unipoll.entity.Poll;
import de.cwansart.unipoll.entity.Vote;
import de.cwansart.unipoll.model.VoteForm;
import de.cwansart.unipoll.repository.PollRepository;
import de.cwansart.unipoll.repository.VoteRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class VoteController {
    @Autowired
    private PollRepository pollRepo;

    @Autowired
    private VoteRepository voteRepo;

    @GetMapping("/vote")
    public String show(@RequestParam(name = "id", required = true) long id, Model model, HttpServletRequest request) {
        Optional<Poll> poll = pollRepo.findById(id);
        if (poll.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
        }
        
        if (poll.get().isDeleted()) {
            return "redirect:/results?id=" + id + "&c=1";
        }

        Optional<Cookie> userIdCookie = request.getCookies() != null ? Arrays.asList(request.getCookies()).stream()
                .filter(c -> c.getName().equals("unipoll-user-id")).findFirst() : Optional.empty();
        // check if user already voted
        if (userIdCookie.isPresent() && voteRepo.findByPollIdAndUserId(id, userIdCookie.get().getValue()).isPresent()) {
            return "redirect:/results?id=" + id + "&v=1";
        }
        model.addAttribute("choices", poll.get().getChoices());
        model.addAttribute("voteForm", new VoteForm());
        model.addAttribute("id", id);
        model.addAttribute("name", poll.get().getName());
        return "vote";
    }

    @PostMapping("/vote")
    public String save(@RequestParam(name = "id", required = true) long id,
            @ModelAttribute("voteForm") VoteForm voteForm, Model model, HttpServletResponse response) {
        Optional<Poll> poll = pollRepo.findById(id);
        if (poll.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll does not exist");
        }
        
        if (poll.get().isDeleted()) {
            return "redirect:/results?id=" + id + "&c=1";
        }

        String userId = UUID.randomUUID().toString();

        // check if user has already voted
        if (voteRepo.findByPollIdAndUserId(id, userId).isPresent()) {
            return "redirect:/results?id=" + id + "&v=1";
        }

        // check if selected ids belong to the given poll
        List<Choice> choices = new ArrayList<>();
        for (String s : voteForm.getSelected()) {
            long sLong = Long.parseLong(s);
            Optional<Choice> choice = poll.get().getChoices().stream().filter(t -> t.getId().equals(sLong)).findFirst();
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

        response.addCookie(new Cookie("unipoll-user-id", userId));

        return "redirect:/vote?id=" + id;
    }
}
