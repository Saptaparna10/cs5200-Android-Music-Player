package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.models.*;
import edu.northeastern.cs5200.repositories.ReviewerRepository;

/**
 * Represents API for Reviewer
 * @author saptaparnadas
 *
 */
@RestController
public class ReviewerService {

	@Autowired
	private ReviewerRepository reviewerRepository;
	
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * Creates a new entry for critic
	 * @param critic
	 * @return
	 */
	@PostMapping("/api/critic")
	public Reviewer createReviewer(@RequestBody Reviewer critic) {
		return reviewerRepository.save(critic);
		
	}
	
	@PostMapping("/api/critic/review/{criticid}/{songid}")
	public Review createReviewFromReviewer(@PathVariable("criticid") int criticid,@PathVariable("songid") int songid,@RequestBody Review review) {
		
		return reviewService.createReview(criticid, songid, review);
	}
	
	/**
	 * Retrieves critic by id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/critic/{id}")
	public Reviewer findReviewerById(@PathVariable("id") int id) {
		Optional<Reviewer> critic =  reviewerRepository.findById(id);
		if(critic != null) {
			return critic.get();
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * Retrieves all the critics
	 * @return
	 */
	@GetMapping("/api/critic")
	public List<Reviewer> findAllReviewers(){
		return (List<Reviewer>) reviewerRepository.findAll();
	}
	
	/**
	 * Retrieves all the reviews given by a critic
	 * @param id
	 * @return
	 */
	@GetMapping("/api/critic/{id}/reviews")
	public List<Review> getAllReviewsForReviewer(@PathVariable ("id") int id){
		return findReviewerById(id).getReviewsGiven();
	}
	
	
	/**
	 * Updates the attributes of a critic
	 * @param id
	 * @param critic
	 * @return
	 */
	@PutMapping("/api/critic/{id}")
	public Reviewer updateReviewer(@PathVariable("id") int id, @RequestBody Reviewer critic) {
		Reviewer prevCritic = findReviewerById(id);
		prevCritic.set(critic);
		return reviewerRepository.save(prevCritic);
	}
	
	
	@PutMapping("/api/critic/review/{reviewid}")
	public Review updateReview(@PathVariable("reviewid") int reviewid, @RequestBody Review review) {
		
		return reviewService.updateReview(reviewid, review);
	}
	
	/**
	 * Retrieve a critic by his/her first and last name
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@GetMapping("/api/critic/name/{firstName}/{lastName}")
	public Reviewer findReviewerByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		List<Reviewer> reviewerList = (List<Reviewer>) reviewerRepository.findCriticByName(firstName, lastName);
		if(reviewerList != null && !reviewerList.isEmpty()) {
			return reviewerList.get(0);
		}
		return null;
	}
	
	
	/**
	 * Delete a particular critic by his/her id
	 * @param id
	 */
	@DeleteMapping("/api/critic/{id}")
	public void deleteReviewer(@PathVariable("id") int id) {
		Reviewer reviewer = findReviewerById(id);
		List<Review> reviewsGiven = reviewer.getReviewsGiven();
		if(reviewsGiven != null && !reviewsGiven.isEmpty()) {
			for(Review reviewGiven: reviewsGiven) {
				reviewGiven.setCritic(null);
				reviewService.updateReview(reviewGiven.getId(), reviewGiven);
			}
		}
		reviewerRepository.deleteById(id);
	}
	
	/**
	 * Deletes all the critics
	 */
	@DeleteMapping("/api/critic")
	public void deleteAllReviewers() {
		List<Reviewer> reviewers = findAllReviewers();
		for(Reviewer reviewer: reviewers) {
			deleteReviewer(reviewer.getId());
		}
	}
	
	@DeleteMapping("/api/critic/review/{reviewid}")
	public void deleteReviewFromReviewer(@PathVariable("reviewid") int reviewid) {
		
		reviewService.deleteReview(reviewid);
	}
	
	/**
	 * Retrieves critic on the basis of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/api/critic/credentials/{username}/{password}")
	public Reviewer findReviewerByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<Reviewer> userList = (List<Reviewer>) reviewerRepository.findCriticByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
}
