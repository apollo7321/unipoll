package de.cwansart.unipoll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.cwansart.unipoll.entity.Poll;
import de.cwansart.unipoll.repository.PollRepository;
import de.cwansart.unipoll.service.AuthService;

@Controller
public class ListController {
	@Autowired
	private PollRepository pollRepo;
	
	@Autowired
	private AuthService auth;
	
	@GetMapping("/list")
	public String showAll(@RequestParam(name = "p", defaultValue = "0") int page, Model model) {
		if (!auth.isAuthenticated()) {
			return "redirect:/login?p=list";
		}
		
		Page<Poll> polls = pollRepo.findAll(PageRequest.of(page, 20, Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("polls", polls.getContent());
		model.addAttribute("totalNum", polls.getTotalElements());
		model.addAttribute("totalPages", polls.getTotalPages());
		model.addAttribute("currentPage", page + 1);
		return "list";
	}
}
