package de.cwansart.unipoll.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import de.cwansart.unipoll.entity.Poll;
import de.cwansart.unipoll.repository.PollRepository;
import de.cwansart.unipoll.service.AuthService;

@Controller
public class ClosePollController {
    @Autowired
    private AuthService auth;

    @Autowired
    private PollRepository pollRepo;

    @GetMapping("/close")
    public String confirm(@RequestParam(name = "id", required = true) long id, Model model) {
        if (!auth.isAuthenticated()) {
            return "redirect:/login?p=list";
        }

        Optional<Poll> poll = pollRepo.findById(id);
        if (poll.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll not found");
        }

        if (poll.isPresent() && poll.get().isDeleted()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "poll already closed");
        }

        model.addAttribute("id", id);
        model.addAttribute("name", poll.get().getName());
        return "confirm_close";
    }

    @PostMapping("/close")
    public String close(@RequestParam(name = "id", required = true) long id, Model model) {
        if (!auth.isAuthenticated()) {
            return "redirect:/login?p=list";
        }

        Optional<Poll> poll = pollRepo.findById(id);
        if (!poll.isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "poll not found");
        }

        if (poll.isPresent() && poll.get().isDeleted()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "poll already closed");
        }

        poll.get().setDeleted(true);
        pollRepo.save(poll.get());
        return "redirect:/list";
    }
}
