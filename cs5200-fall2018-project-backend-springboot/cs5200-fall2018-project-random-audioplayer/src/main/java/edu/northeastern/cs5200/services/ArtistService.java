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
import edu.northeastern.cs5200.repositories.ArtistRepository;

/**
 * Represents API for Artist
 * @author saptaparnadas
 *
 */
@RestController
public class ArtistService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private UserService registeredUserService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * Creates an artist	
	 * @param artist
	 * @return
	 */
	@PostMapping("/api/artist")
	public Artist createArtist(@RequestBody Artist artist) {
		return artistRepository.save(artist);
		
	}
	
	@PostMapping("/api/artist/song")
	public Song createSongFromArtist(@RequestBody Song song) {
		return songService.createSong(song);
		
	}
	
	/**
	 * fetches an artist by it's id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/artist/{id}")
	public Artist findArtistById(@PathVariable("id") int id) {
		Optional<Artist> artist =  artistRepository.findById(id);
		if(artist != null) {
			return artist.get();
		}
		else {
			return null;
		}
	}
	
	/**
	 * fetches all artists
	 * @return
	 */
	@GetMapping("/api/artist")
	public List<Artist> findAllArtists(){
		return (List<Artist>) artistRepository.findAll();
	}
	
	@GetMapping("/api/artist/song/{id}")
	public Song getSongFromArtist(@PathVariable("id") int id) {
		
		return songService.findSongById(id);
	}
	
	
	/**
	 * Update an artist
	 * @param id
	 * @param artist
	 * @return
	 */
	@PutMapping("/api/artist/{id}")
	public Artist updateArtist(@PathVariable int id, @RequestBody Artist artist) {
		Artist prevArtist = findArtistById(id);
		prevArtist.set(artist);
		return artistRepository.save(prevArtist);
		
	}
	
	
	/**
	 * Retrieves an artist by it's name
	 * @param name
	 * @return
	 */
	@GetMapping("/api/artist/name/{name}")
	public Artist findArtistByName(@PathVariable String name) {
		
		List<Artist> list =  (List<Artist>) artistRepository.findArtistByName(name);
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * artist follows another fellow artist
	 * @param artistidFollower
	 * @param artistidFollowing
	 */
	@PutMapping("/api/artist/follow/{artistidFollower}/{artistidFollowing}")
	public void followAnotherArtist(@PathVariable("artistidFollower") int artistidFollower, @PathVariable("artistidFollowing") int artistidFollowing) {
		Artist artistFollower = findArtistById(artistidFollower);
		Artist artistFollowing = findArtistById(artistidFollowing);
		artistFollower.addArtistToFollowing(artistFollowing);
		updateArtist(artistidFollowing, artistFollowing);
		updateArtist(artistidFollower, artistFollower);
		
	}
	
	
	@PutMapping("/api/artist/song/{id}")
	public void updateSongFromArtist(@PathVariable int id, @RequestBody Song song) {
		
		songService.updateSong(id, song);
	}
		
	/**
	 * artist un-follows another fellow artist
	 * @param artistidFollower
	 * @param artistidFollowing
	 */
	@DeleteMapping("/api/artist/follow/{artistidFollower}/{artistidFollowing}")
	public void unfollowArtist(@PathVariable("artistidFollower") int artistidFollower, @PathVariable("artistidFollowing") int artistidFollowing) {
		Artist artistFollower = findArtistById(artistidFollower);
		Artist artistFollowing = findArtistById(artistidFollowing);
		artistFollower.removeArtistFromFollowing(artistFollowing);
		updateArtist(artistidFollowing, artistFollowing);
		updateArtist(artistidFollower, artistFollower);
	}
	
	/**
	 * fetches all artists that a particular artist is following
	 * @param id
	 * @return
	 */
	@GetMapping("/api/artist/following/{id}")
	public List<Artist> findAllFollowingArtists(@PathVariable ("id") int id){
		return findArtistById(id).getArtistsFollowing();
	}
	
	/**
	 * fetches all artists that are followers of a particular artist
	 * @param id
	 * @return
	 */
	@GetMapping("/api/artist/follower/{id}")
	public List<Artist> findAllFollowerArtists(@PathVariable("id") int id){
		return findArtistById(id).getArtistFollowers();
	}
	

	/**
	 * fetches all registered users that are followers of a particular atrist
	 * @param id
	 * @return
	 */
	@GetMapping("/api/artist/registereduser/follower/{id}")
	public List<User> findAllFollowerRegUsers(@PathVariable("id") int id){
		return findArtistById(id).getFollowers();
	}
	
	/**
	 * fetches all admin users that are followers of a particular artist
	 * @param id
	 * @return
	 */
	@GetMapping("/api/artist/admin/follower/{id}")
	public List<Admin> findAllFollowerAdminUsers(@PathVariable("id") int id){
		return findArtistById(id).getAdminFollowers();
	}
	
	/**
	 * Deletes a particular artist by it's id
	 * @param id
	 */
	@DeleteMapping("/api/artist/{id}")
	public void deleteArtist(@PathVariable("id") int id) {

		Artist artist = findArtistById(id);
		List<Song> songs = artist.getSongs();
		if(songs != null && !songs.isEmpty()) {
			for(Song song: songs) {
				song.getArtists().remove(artist);
				songService.updateSong(song.getId(), song);
			}
		}
		
		List<User> followers = artist.getFollowers();
		if(followers != null && !followers.isEmpty()) {
			for(User user: followers) {
				user.getFollowing().remove(artist);
				registeredUserService.updateUser(user.getId(), user);
			}
		}
		
		List<Admin> adminFollowers = artist.getAdminFollowers();
		if(adminFollowers != null && !adminFollowers.isEmpty()) {
			for(Admin user: adminFollowers) {
				user.getFollowing().remove(artist);
				adminService.updateAdmin(user.getId(), user);
			}
		}
		
		List<Artist> artistFollowers = artist.getArtistFollowers();
		if(artistFollowers != null && !artistFollowers.isEmpty()) {
			for(Artist artistFollower: artistFollowers) {
				artistFollower.getArtistsFollowing().remove(artist);
				updateArtist(artistFollower.getId(), artistFollower);
			}
		}
		
		List<Artist> artistFollowingList = artist.getArtistsFollowing();
		if(artistFollowingList != null && !artistFollowingList.isEmpty()) {
			for(Artist artistFollowing: artistFollowingList) {
				artistFollowing.getArtistFollowers().remove(artist);
				updateArtist(artistFollowing.getId(), artistFollowing);
			}
		}
		
		artistRepository.deleteById(artist.getId());
	}
	
	/**
	 * Deletes all artists
	 */
	@DeleteMapping("/api/artist")
	public void deleteAllArtists() {
		List<Artist> artists = findAllArtists();
		for(Artist artist: artists) {
			deleteArtist(artist.getId());
		}
	}
	
	@DeleteMapping("/api/artist/song/{id}")
	public void deleteSongFromArtist(@PathVariable("id") int id) {
		
		songService.deleteSong(id);
	}
	
	
	/**
	 * fetches artist on the basis of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/api/artist/credentials/{username}/{password}")
	public Artist findArtistByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<Artist> userList = (List<Artist>) artistRepository.findArtistByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
	

}
