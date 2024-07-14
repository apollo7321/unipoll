package de.cwansart.unipoll;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface PollRepository extends Repository<Poll, Long> {
	Poll save(Poll poll);
	Optional<Poll> findById(Long id);
}
