package edu.northeastern.cs5200.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the User class.
 * 
 * @author Saptaparna
 *
 */
@Entity
public class User extends Person {

	// instance variables
	private String planDetails;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;

	@OneToMany(mappedBy = "owner")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Playlist> playlists;
	
	@OneToMany(mappedBy = "user")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<SongLike> likes;

	@ManyToMany
	@JoinTable(name = "UserArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Artist> following;

	/**
	 * Represents the default constructor
	 */
	public User() {
		super();
		playlists = new ArrayList<>();
		following = new ArrayList<>();
		likes = new ArrayList<>();
	}

	// Getters and Setters
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

	public List<SongLike> getLikes() {
		return likes;
	}

	public void setLikes(List<SongLike> likes) {
		this.likes = likes;
	}

	
	/**
	 * Adds the artist to following list
	 * 
	 * @param artist
	 */
	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if (!artist.getFollowers().contains(this)) {
			artist.getFollowers().add(this);
		}
	}

	/**
	 * Removes artist from following list
	 * 
	 * @param artist
	 */
	public void removeArtistFromFollowing(Artist artist) {
		this.following.remove(artist);
		if (artist.getFollowers().contains(this)) {
			artist.getFollowers().remove(this);
		}
	}
	
	/**
	 * Updates user
	 * 
	 * @param user
	 */
	public void set(User user) {
		super.set(user);
		this.setPlanDetails(user.getPlanDetails() != null ? user.getPlanDetails() : this.getPlanDetails());
		this.setSubscriptionEndDate(
				user.getSubscriptionEndDate() != null ? user.getSubscriptionEndDate() : this.getSubscriptionEndDate());
		this.setSubscriptionStartDate(user.getSubscriptionStartDate() != null ? user.getSubscriptionStartDate()
				: this.getSubscriptionStartDate());

		if (user.getPlaylists() != null) {
			if (this.getPlaylists() == null) {
				this.setPlaylists(user.getPlaylists());
			} else if (!this.getPlaylists().equals(user.getPlaylists())) {
				this.setPlaylists(user.getPlaylists());
			}
		}

		if (user.getFollowing() != null) {
			if (this.getFollowing() == null) {
				this.setFollowing(user.getFollowing());
			} else if (!this.getFollowing().equals(user.getFollowing())) {
				this.setFollowing(user.getFollowing());
			}
		}
	}

	/**
	 * Overridden version of the equals method. Two registered users are considered
	 * equal only if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			if (this.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

}
