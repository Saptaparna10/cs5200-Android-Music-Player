package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.SongLike;
import edu.northeastern.cs5200.models.Song;
import edu.northeastern.cs5200.models.User;

/**
 * Represents repository class for likes
 * @author saptaparnadas
 *
 */
public interface SongLikeRepository extends CrudRepository<SongLike, Integer>{
	
	@Query("SELECT r FROM SongLike r WHERE r.song=:song")
	Iterable<SongLike> findLikeBySong(@Param("song") Song song);
	
	@Query("SELECT r FROM SongLike r WHERE r.user=:user")
	Iterable<SongLike> findLikeByUser(@Param("user") User user);
}
