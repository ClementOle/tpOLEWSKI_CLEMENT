package com.CGI.OLEWSKI_CLEMENT.user;


import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	//////Create///////

	//Création d'un user
	@PostMapping
	public User postUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	//////Read///////

	//Récupération d'un user
	@GetMapping("{id}")
	public User getUser(@PathVariable(value = "id") int id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
	}

	//Récupération de tout les user
	@GetMapping
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

}
