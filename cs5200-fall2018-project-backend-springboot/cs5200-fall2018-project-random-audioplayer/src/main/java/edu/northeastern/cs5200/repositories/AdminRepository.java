package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.models.Admin;

/**
 * Represents repository class for Admin
 * @author saptaparnadas
 *
 */
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	
	@Query("SELECT u FROM Admin u WHERE u.username=:username and u.password=:password")
	Iterable<Admin> findAdminByCredentials(@Param("username") String username, @Param("password") String password);

}
