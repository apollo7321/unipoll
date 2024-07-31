package de.cwansart.unipoll.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.cwansart.unipoll.entity.Poll;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
	Poll save(Poll poll);
	Optional<Poll> findById(Long id);
}
