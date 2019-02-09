package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Person;

/**
 * Represents repository class for Person
 * @author saptaparnadas
 *
 */
public interface PersonRepository extends CrudRepository<Person,Integer> {
	
	@Query("SELECT u FROM Person u WHERE u.username=:username and u.password=:password")
	Iterable<Person> findPersonByCredentials(@Param("username") String username, @Param("password") String password);

}
