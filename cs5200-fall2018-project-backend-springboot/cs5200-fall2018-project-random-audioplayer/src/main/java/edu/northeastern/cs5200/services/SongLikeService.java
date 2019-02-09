package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.models.Playlist;
import edu.northeastern.cs5200.models.Song;
import edu.northeastern.cs5200.models.SongLike;
import edu.northeastern.cs5200.models.User;
import edu.northeastern.cs5200.repositories.SongLikeRepository;
import edu.northeastern.cs5200.repositories.SongRepository;
import edu.northeastern.cs5200.repositories.UserRepository;

/**
 * Represents API for like
 * @author saptaparnadas
 *
 */
@RestController
public class SongLikeService {

	@Autowired
	SongLikeRepository songLikeRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SongRepository songRepo;
	
	/**
	 * creates like
	 * @param id
	 * @return
	 */
	@PostMapping("/api/like")
	public SongLike createLike(@RequestBody SongLike songLike) {
		return songLikeRepo.save(songLike);
	}
	

	/**
	 * fetches like by song
	 * @param id
	 * @return
	 */
	@GetMapping("/api/like/song/{id}")
	public Iterable<SongLike> findLikeBySong(@PathVariable("id") int id) {
		Optional<Song> opt= songRepo.findById(id);
		Song song = opt.get();
		Iterable<SongLike> songLike=  songLikeRepo.findLikeBySong(song);
		if(songLike != null) {
			return songLike;
		}
		else {
			return null;
		}
	}
	
	/**
	 * fetches like by user
	 * @param id
	 * @return
	 */
	@GetMapping("/api/like/user/{id}")
	public Iterable<SongLike> findLikeByUser(@PathVariable("id") int id) {
		Optional<User> opt= userRepo.findById(id);
		User user = opt.get();

		Iterable<SongLike>  songLike=  songLikeRepo.findLikeByUser(user);
		if(songLike != null) {
			return songLike;
		}
		else {
			return null;
		}
	}
	
}
