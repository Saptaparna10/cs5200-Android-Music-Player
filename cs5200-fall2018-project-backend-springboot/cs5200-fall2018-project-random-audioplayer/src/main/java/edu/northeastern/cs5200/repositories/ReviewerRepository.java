package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Admin;
import edu.northeastern.cs5200.models.Artist;
import edu.northeastern.cs5200.models.Reviewer;

/**
 * Represents repository class for Reviewer
 * @author saptaparnadas
 *
 */
public interface ReviewerRepository extends CrudRepository<Reviewer, Integer>{
	
	@Query("SELECT c FROM Reviewer c WHERE c.firstName=:firstName and c.lastName=:lastName")
	Iterable<Reviewer> findCriticByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
	@Query("SELECT c FROM Reviewer c WHERE c.username=:username and c.password=:password")
	Iterable<Reviewer> findCriticByCredentials(@Param("username") String username, @Param("password") String password);

}
