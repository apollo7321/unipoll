package de.cwansart.unipoll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import de.cwansart.unipoll.entity.Vote;

public interface VoteRepository extends Repository<Vote, Long> {
	Vote save(Vote vote);
	Optional<Vote> findById(Long id);
	Optional<Vote> findByPollIdAndUserId(Long pollId, String userId);
	List<Vote> findByPollId(Long pollId);
	long countByPollId(Long pollId);
	long countById(Long id);
}
