package de.cwansart.unipoll;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
	Poll save(Poll poll);
	Optional<Poll> findById(Long id);
}
