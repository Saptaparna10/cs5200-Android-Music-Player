package edu.northeastern.cs5200.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Artist class.
 * 
 * @author Saptaparna
 *
 */
@Entity
public class Artist extends Person {

	// instance variables
	private String name;
	private Long playCount;
	private Long listeners;
	private String detailsUrl;
	private String imageUrl;

	@ManyToMany
	@JoinTable(name = "Artist2Song")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Song> songs;

	@ManyToMany(mappedBy = "following")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<User> followers;

	@ManyToMany(mappedBy = "following")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Admin> adminFollowers;

	@ManyToMany(mappedBy = "artistsFollowing")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Artist> artistFollowers;

	@ManyToMany
	@JoinTable(name = "ArtistArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Artist> artistsFollowing;

	/**
	 * Default Constructor
	 */
	public Artist() {
		super();
		songs = new ArrayList<>();
		followers = new ArrayList<>();
		adminFollowers = new ArrayList<>();
	}

	// Getters and Setters
	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<Admin> getAdminFollowers() {
		return adminFollowers;
	}

	public void setAdminFollowers(List<Admin> adminFollowers) {
		this.adminFollowers = adminFollowers;
	}

	public List<Artist> getArtistFollowers() {
		return artistFollowers;
	}

	public void setArtistFollowers(List<Artist> artistFollowers) {
		this.artistFollowers = artistFollowers;
	}

	public List<Artist> getArtistsFollowing() {
		return artistsFollowing;
	}

	public void setArtistsFollowing(List<Artist> artistsFollowing) {
		this.artistsFollowing = artistsFollowing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	public Long getListeners() {
		return listeners;
	}

	public void setListeners(Long listeners) {
		this.listeners = listeners;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Updates artist obj
	 * 
	 * @param artist
	 */
	public void set(Artist artist) {

		super.set(artist);
		this.setName(artist.getName() != null ? artist.getName() : this.getName());
		this.setImageUrl(artist.getImageUrl() != null ? artist.getImageUrl() : this.getImageUrl());
		this.setDetailsUrl(artist.getDetailsUrl() != null ? artist.getDetailsUrl() : this.getDetailsUrl());
		this.setListeners(artist.getListeners() != null ? artist.getListeners() : this.getListeners());
		this.setPlayCount(artist.getPlayCount() != null ? artist.getPlayCount() : this.getPlayCount());

		if (artist.getSongs() != null) {
			if (this.getSongs() == null) {
				this.setSongs(artist.getSongs());
			} else if (!artist.getSongs().equals(this.getSongs())) {
				this.setSongs(artist.getSongs());
			}
		}

		if (artist.getFollowers() != null) {
			if (this.getFollowers() == null) {
				this.setFollowers(artist.getFollowers());
			} else if (!artist.getFollowers().equals(this.getFollowers())) {
				this.setFollowers(artist.getFollowers());
			}
		}

	}

	/**
	 * adds artist to following list
	 * 
	 * @param artist
	 */
	public void addArtistToFollowing(Artist artist) {
		if (!this.getArtistsFollowing().contains(artist)) {
			this.getArtistsFollowing().add(artist);
		}
		if (!artist.getArtistFollowers().contains(this)) {
			artist.getArtistFollowers().add(this);
		}
	}

	/**
	 * Remove the artist from following
	 * @param artist
	 */
	public void removeArtistFromFollowing(Artist artist) {
		if (this.getArtistsFollowing().contains(artist)) {
			this.getArtistsFollowing().remove(artist);
		}
		if (artist.getArtistFollowers().contains(this)) {
			artist.getArtistFollowers().remove(this);
		}
	}

	/**
	 * Overridden version of the equals method. Two artists are considered equal only
	 * if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Artist) {
			Artist artist = (Artist) obj;
			if (this.getId() == artist.getId()) {
				return true;
			}
		}
		return false;
	}

}
