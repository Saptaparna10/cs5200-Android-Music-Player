package edu.northeastern.cs5200.services;

import java.util.ArrayList;
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
import edu.northeastern.cs5200.repositories.ArtistRepository;
import edu.northeastern.cs5200.repositories.UserRepository;

/**
 * Represents API for user
 * @author saptaparnadas
 *
 */
@RestController
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArtistService artistService;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	PlaylistService playlistService;
	
	/**
	 * Creates new user
	 * @param registeredUser
	 * @return
	 */
	@PostMapping("/api/registereduser")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
		
	}
	
	
	/**
	 * fetches user by his id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/registereduser/{id}")
	public User findUserById(@PathVariable("id") int id) {
		Optional<User> user =  userRepository.findById(id);
		if(user != null) {
			return user.get();
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * fetches all users
	 * @return
	 */
	@GetMapping("/api/registereduser")
	public List<User> findAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	
	@GetMapping("/api/registereduser/playlist/{id}")
	public Playlist getPlaylistFromUser(@PathVariable("id") int id) {
		
		return playlistService.findPlaylistById(id);
	}
	
	
	/**
	 * Updates user
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/api/registereduser/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
		User prevUser = findUserById(id);
		prevUser.set(user);
		return userRepository.save(prevUser);
	}
	
	
	/**
	 * Makes user follow an artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@PutMapping("/api/registereduser/follow/{userid}/{artistid}")
	public User followArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		User user = findUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.addArtistToFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateUser(userid, user);

	}
	
	@PutMapping("/api/registereduser/playlist/{id}")
	public Playlist updatePlaylistFromUser(@PathVariable("id") int id,@RequestBody Playlist playlist) {
		
		return playlistService.updatePlaylist(id, playlist);
	}
	
	
	/**
	 * Makes user un-follow an artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@DeleteMapping("/api/registereduser/follow/{userid}/{artistid}")
	public User unfollowArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		User user = findUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.removeArtistFromFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateUser(userid, user);
	}
	
	/**
	 * Deletes a user by id
	 * @param id
	 */
	@DeleteMapping("/api/registereduser/{id}")
	public void deleteUser(@PathVariable ("id") int id) {
		User user = findUserById(id);
		List<Playlist> playlists = user.getPlaylists();
		List<Integer> temp = new ArrayList<>();
		if(playlists != null && !playlists.isEmpty()) {
			for(Playlist pl: playlists) {
				pl.setOwner(null);
				temp.add(pl.getId());
				playlistService.updatePlaylist(pl.getId(), pl);
			}
			
		}
		
		List<Artist> following = user.getFollowing();
		if(following != null && !following.isEmpty()) {
			for(Artist artist: following) {
				artist.getFollowers().remove(user);
				artistService.updateArtist(artist.getId(), artist);
			}
		}
		
		userRepository.deleteById(user.getId());
		if(temp != null && !temp.isEmpty()) {
			for(Integer plId: temp) {
				playlistService.deletePlaylist(plId);
			}
		}
	}
	
	/**
	 * Deletes all users
	 */
	@DeleteMapping("/api/registereduser")
	public void deleteAllUsers() {
		List<User> userList = findAllUsers();
		for(User user : userList) {
			deleteUser(user.getId());
		}
	}
	
	
	@DeleteMapping("/api/registereduser/playlist/{playlistId}")
	public void deletePlaylist(@PathVariable ("playlistId") int playlistId) {
		 playlistService.deletePlaylist(playlistId);
	}
	
	/**
	 * fetches all the playlists for user
	 * @param id
	 * @return
	 */
	@GetMapping("/api/registereduser/{id}/playlists")
	public List<Playlist> getAllPlaylists(@PathVariable ("id") int id) {
		User user = findUserById(id);
		if(user != null) {
			return user.getPlaylists();
		}
		return null;
	}
	
	
	/**
	 * fetches all the artists that a particular user is following
	 * @param id
	 * @return
	 */
	@GetMapping("/api/registereduser/{id}/following")
	public List<Artist> getArtistsFollowing(@PathVariable ("id") int id){
		User user = findUserById(id);
		if(user != null) {
			return user.getFollowing();
		}
		return null;
	}
	
	/**
	 * fetches user on the basis of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/api/registereduser/credentials/{username}/{password}")
	public User findUserByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<User> userList = (List<User>) userRepository.findUserByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

}
