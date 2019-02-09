package edu.northeastern.cs5200.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Reviewer.
 * 
 * @author Saptaparna
 *
 */
@Entity
public class Reviewer extends Person {

	// instance variables
	@Column(nullable = true)
	private String careerDescription;

	@Column(nullable = true)
	private Integer numberOfAwards;

	@OneToMany(mappedBy = "critic")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Review> reviewsGiven;

	/**
	 * Represents the default constructor
	 */
	public Reviewer() {
		super();
		reviewsGiven = new ArrayList<>();
	}

	public String getCareerDescription() {
		return careerDescription;
	}

	public void setCareerDescription(String careerDescription) {
		this.careerDescription = careerDescription;
	}

	public Integer getNumberOfAwards() {
		return numberOfAwards;
	}

	public void setNumberOfAwards(Integer numberOfAwards) {
		this.numberOfAwards = numberOfAwards;
	}

	public List<Review> getReviewsGiven() {
		return reviewsGiven;
	}

	public void setReviewsGiven(List<Review> reviewsGiven) {
		this.reviewsGiven = reviewsGiven;
	}

	/**
	 * Updates the reviewer
	 * 
	 * @param critic
	 */
	public void set(Reviewer reviewer) {
		super.set(reviewer);
		this.setCareerDescription(
				reviewer.getCareerDescription() != null ? reviewer.getCareerDescription() : this.getCareerDescription());
		this.setNumberOfAwards(
				reviewer.getNumberOfAwards() != null ? reviewer.getNumberOfAwards() : this.getNumberOfAwards());

		if (reviewer.getReviewsGiven() != null) {
			if (this.getReviewsGiven() == null) {
				this.setReviewsGiven(reviewer.getReviewsGiven());
			} else if (!this.getReviewsGiven().equals(reviewer.getReviewsGiven())) {
				this.setReviewsGiven(reviewer.getReviewsGiven());
			}
		}
	}

	/**
	 * Overridden version of the equals method. Two critics are considered equal
	 * only if they have the same id.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Reviewer) {
			Reviewer critic = (Reviewer) obj;
			if (this.getId() == critic.getId()) {
				return true;
			}
		}
		return false;
	}

}
