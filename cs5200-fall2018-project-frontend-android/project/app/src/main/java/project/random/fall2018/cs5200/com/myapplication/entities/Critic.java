package project.random.fall2018.cs5200.com.myapplication.entities;

import java.util.ArrayList;
import java.util.List;



public class Critic extends User {
	

	private String careerDescription;
	

	private Integer numberOfAwards;
	

	private List<Review> reviewsGiven;
	
	public Critic() {
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
	
	public void set(Critic critic) {
		super.set(critic);
		this.setCareerDescription(critic.getCareerDescription() != null ? critic.getCareerDescription() : this.getCareerDescription());
		this.setNumberOfAwards(critic.getNumberOfAwards() != null ? critic.getNumberOfAwards() : this.getNumberOfAwards());
		
		if(critic.getReviewsGiven() != null) {
			if(this.getReviewsGiven() == null) {
				this.setReviewsGiven(critic.getReviewsGiven());
			}
			else if(!this.getReviewsGiven().equals(critic.getReviewsGiven())) {
				this.setReviewsGiven(critic.getReviewsGiven());
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Critic) {
			Critic critic = (Critic) obj;
			if(this.getId() == critic.getId()) {
				return true;
			}
		}
		return false;
	}
	
	

}
