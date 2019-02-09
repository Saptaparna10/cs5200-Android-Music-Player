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
import edu.northeastern.cs5200.repositories.AdminRepository;

/**
 * Represents API for Admin
 * @author saptaparnadas
 *
 */
@RestController
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	ArtistService artistService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserService userService;
	
	
	
	/**
	 * Creates an admin 
	 * @param admin
	 * @return
	 */
	@PostMapping("/api/adminuser")
	public Admin createAdmin(@RequestBody Admin admin) {
		return adminRepository.save(admin);
	}
	
	
	
	@PostMapping("/api/adminuser/playlist")
	public Playlist createPlaylistWithAdmin(@RequestBody Playlist playlist) {
		
		return playlistService.createPlaylist(playlist);
	}
	
	
	@PostMapping("/api/adminuser/review")
	public Review createReviewWithAdmin(@PathVariable("criticId") int criticId, @PathVariable("songId") int songId,@RequestBody Review review) {
		
		return reviewService.createReview(criticId,songId,review);
	}
	
	
	@PostMapping("/api/adminuser/user")
	public User createUserWithAdmin(@RequestBody User user) {
		
		return userService.createUser(user);
	}
	
	/**
	 * fetches details of an admin by id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/adminuser/{id}")
	public Admin findAdminById(@PathVariable("id") int id) {
		Optional<Admin> admin =  adminRepository.findById(id);
		if(admin != null) {
			return admin.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/adminuser/user")
	public List<User> findAllUserFromAdmin() {
		
		return (List<User>) userService.findAllUsers();
	}
	
	/**
	 * fetches all the admins
	 * @return
	 */
	@GetMapping("/api/adminuser")
	public List<Admin> findAllAdmin(){
		return (List<Admin>) adminRepository.findAll();
	}
	
	@GetMapping("/api/adminuser/playlist")
	public List<Playlist> findAllPlaylistFromAdmin(){
		
		return (List<Playlist>)playlistService.findAllPlaylists();
	}
	
	
	@GetMapping("/api/adminuser/review")
	public List<Review> findAllReviewsFromAdmin(){
		
		return (List<Review>)reviewService.findAllReviews();
	}
	
	
	
	
	
	/**
	 * Updates an admin
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/api/adminuser/{id}")
	public Admin updateAdmin(@PathVariable("id") int id, @RequestBody Admin user) {
		Admin prevUser = findAdminById(id);
		prevUser.set(user);
		return adminRepository.save(prevUser);
	}
	
	//updates a playlist
	
	@PutMapping("/api/adminuser/playlist/{playlistid}")
	public Playlist updatePlaylist(@PathVariable("playlistid") int playlistid,@RequestBody Playlist playlist) {
		
		return playlistService.updatePlaylist(playlistid, playlist);
	}
	
	
	@PutMapping("/api/adminuser/user/{id}")
	public User updateUserFromAdmin(@PathVariable("id") int id, @RequestBody User user) {
		
		return userService.updateUser(id, user);
	}
	
	/**
	 * Makes a particular admin follow a particular artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@PutMapping("/api/adminuser/follow/{userid}/{artistid}")
	public Admin followArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		Admin user = findAdminById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.addArtistToFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateAdmin(userid, user);

	}

	@DeleteMapping("/api/admin/user/{id}")
	public void deleteUser(@PathVariable ("id") int id) {
		
		userService.deleteUser(id);
	}
	
	
	@DeleteMapping("/api/admin/playlist/")
	public void deletePlaylist() {
		playlistService.deleteAllPlaylists();	
	}
	
}