package project.random.fall2018.cs5200.com.myapplication.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class RegisteredUser extends User {
	
	private String planDetails;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	

	private List<Playlist> playlists;

	private List<Artist> following;
	
	public RegisteredUser() {
		super();
		playlists = new ArrayList<>();
		following = new ArrayList<>();
	}

	public String getPlanDetails() {
		return planDetails;
	}

	public void setPlanDetails(String planDetails) {
		this.planDetails = planDetails;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	
	public List<Artist> getFollowing() {
		return following;
	}

	public void setFollowing(List<Artist> following) {
		this.following = following;
	}

	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if(!artist.getFollowers().contains(this)) {
			artist.getFollowers().add(this);
		}
	}
	
	public void removeArtistFromFollowing(Artist artist) {
		this.following.remove(artist);
		if(artist.getFollowers().contains(this)) {
			artist.getFollowers().remove(this);
		}
	}
	
	public void set(RegisteredUser user) {
		super.set(user);
		this.setPlanDetails(user.getPlanDetails() != null ? user.getPlanDetails() : this.getPlanDetails());
		this.setSubscriptionEndDate(user.getSubscriptionEndDate() != null ? user.getSubscriptionEndDate() : this.getSubscriptionEndDate());
		this.setSubscriptionStartDate(user.getSubscriptionStartDate() != null ? user.getSubscriptionStartDate() : this.getSubscriptionStartDate());
		
		if(user.getPlaylists() != null) {
			if(this.getPlaylists() == null) {
				this.setPlaylists(user.getPlaylists());
			}
			else if (!this.getPlaylists().equals(user.getPlaylists())) {
				this.setPlaylists(user.getPlaylists());
			}
		}
		
		if(user.getFollowing() != null) {
			if(this.getFollowing() == null) {
				this.setFollowing(user.getFollowing());
			}
			else if (!this.getFollowing().equals(user.getFollowing())) {
				this.setFollowing(user.getFollowing());
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RegisteredUser) {
			RegisteredUser user = (RegisteredUser) obj;
			if(this.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}
	
	
	
	

}
