package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Album;
import edu.northeastern.cs5200.models.Song;

/**
 * Represents repository class for Album
 * @author saptaparnadas
 *
 */
public interface AlbumRepository extends CrudRepository<Album, Integer>{
	
	@Query("SELECT a FROM Album a WHERE a.name=:name")
	Iterable<Album> findAlbumByName(@Param("name") String name);

}
