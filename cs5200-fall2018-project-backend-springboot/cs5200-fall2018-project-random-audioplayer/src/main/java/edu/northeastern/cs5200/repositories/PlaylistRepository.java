package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Playlist;
import edu.northeastern.cs5200.models.Song;

/**
 * Represents repository class for PlayList
 * @author saptaparnadas
 *
 */
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
	
	@Query("SELECT p FROM Playlist p WHERE p.name=:name")
	Iterable<Playlist> findPlaylistByName(@Param("name") String name);
	

}
