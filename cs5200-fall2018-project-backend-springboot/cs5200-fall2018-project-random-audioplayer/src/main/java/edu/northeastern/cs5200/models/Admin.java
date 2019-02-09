package edu.northeastern.cs5200.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Represents the Admin Class. 
 * 
 * @author Saptaparna
 *
 */
@Entity
public class Admin extends Person {

	// instance variables
	private Date startDate;

	@ManyToMany
	@JoinTable(name = "AdminArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Artist> following;

	/**
	 * Default Constructor
	 */
	public Admin() {
		super();
		following = new ArrayList<>();
	}

	// Getters and Setters
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Artist> getFollowing() {
		return following;
	}

	public void setFollowing(List<Artist> following) {
		this.following = following;
	}

	/**
	 * adds artist to following list
	 * @param artist
	 */
	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if (!artist.getAdminFollowers().contains(this)) {
			artist.getAdminFollowers().add(this);
		}
	}

	/**
	 * removes artist from following list
	 * @param artist
	 */
	public void removeArtistFromFollowing(Artist artist) {
		this.following.remove(artist);
		if (artist.getAdminFollowers().contains(this)) {
			artist.getAdminFollowers().remove(this);
		}
	}

	/**
	 * Updates current admin obj
	 * 
	 * @param admin
	 */
	public void set(Admin admin) {

		super.set(admin);
		this.setStartDate(admin.getStartDate() != null ? admin.getStartDate() : this.getStartDate());

		if (admin.getFollowing() != null) {
			if (this.getFollowing() == null) {
				this.setFollowing(admin.getFollowing());
			} else if (!this.getFollowing().equals(admin.getFollowing())) {
				this.setFollowing(admin.getFollowing());
			}
		}

	}

	/**
	 * Overridden version of the equals method Two admins are considered equal only
	 * if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Admin) {
			Admin admin = (Admin) obj;
			if (this.getId() == admin.getId()) {
				return true;
			}
		}
		return false;
	}

}
