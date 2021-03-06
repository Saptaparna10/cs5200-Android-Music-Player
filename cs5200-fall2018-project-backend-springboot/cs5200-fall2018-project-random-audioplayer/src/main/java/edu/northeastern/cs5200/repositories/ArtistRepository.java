package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Admin;
import edu.northeastern.cs5200.models.Artist;
import edu.northeastern.cs5200.models.Song;

/**
 * Represents repository class for Artist
 * @author saptaparnadas
 *
 */
public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	
	@Query("SELECT a FROM Artist a WHERE a.name=:name")
	Iterable<Artist> findArtistByName(@Param("name") String name);
	
	@Query("SELECT a FROM Artist a WHERE a.username=:username and a.password=:password")
	Iterable<Artist> findArtistByCredentials(@Param("username") String username, @Param("password") String password);

}
