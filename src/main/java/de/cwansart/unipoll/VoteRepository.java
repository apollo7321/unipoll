package de.cwansart.unipoll;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface VoteRepository extends Repository<Vote, Long> {
	Vote save(Vote vote);
	Optional<Vote> findById(Long id);
	Optional<Vote> findByIdAndUserId(Long id, String userId);
	List<Vote> findByPollId(Long pollId);
	long countById(Long id);
}
