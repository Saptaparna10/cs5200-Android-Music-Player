package edu.northeastern.cs5200.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.*;

/**
 * Represents repository class for Review
 * @author saptaparnadas
 *
 */
public interface ReviewRepository extends CrudRepository<Review, Integer> {
	
	@Query("SELECT r FROM Review r WHERE r.critic=:critic and r.song=:song")
	Iterable<Review> findReviewByCrticSong(@Param("critic") Reviewer critic, @Param("song") Song song);

	@Query("SELECT r FROM Review r WHERE r.song=:song")
	Iterable<Review> findAllReviewsForSong(@Param("song") Song song);

}
