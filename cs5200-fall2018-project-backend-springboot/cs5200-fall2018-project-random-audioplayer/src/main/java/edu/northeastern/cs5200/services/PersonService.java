package edu.northeastern.cs5200.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.models.Person;
import edu.northeastern.cs5200.repositories.PersonRepository;

@RestController
public class PersonService {
	
	@Autowired
	PersonRepository userRepository;
	
	@GetMapping("/api/user/credentials/{username}/{password}")
	public Person findUserByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<Person> userList = (List<Person>) userRepository.findPersonByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
	
	@GetMapping("/api/user/{id}")
	public Person findUserById(@PathVariable("id") int id) {
		return userRepository.findById(id).get();
	}
	
	@GetMapping("/api/user")
	public List<Person> findAllUsers(){
		return (List<Person>) userRepository.findAll();
	}
	
	@PutMapping("/api/user/{id}")
	public Person updateUser(@PathVariable("id") int id, @RequestBody Person user) {
		Person prevUser = findUserById(id);
		prevUser.set(user);
		return userRepository.save(prevUser);
	}
	
	@DeleteMapping("/api/user/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		userRepository.deleteById(id);	
	}
	
	@DeleteMapping("/api/user")
	public void deleteAllUsers() {
		List<Person> users = findAllUsers();
		for(Person user: users) {
			deleteUser(user.getId());
		}
	}
}
