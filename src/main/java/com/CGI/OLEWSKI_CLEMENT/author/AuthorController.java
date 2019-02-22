package com.CGI.OLEWSKI_CLEMENT.author;

import com.CGI.OLEWSKI_CLEMENT.book.BookRepository;
import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	BookRepository bookRepository;


	//////Create///////

	//Création d'un auteur
	@PostMapping
	public Author postAuthor(@Valid @RequestBody Author author) {
		return authorRepository.save(author);
	}

	//////Read///////

	//Récupération d'un auteur
	@GetMapping("{id}")
	public Author getAuthor(@PathVariable(value = "id") int id) {
		return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("author", "id", id));
	}

	//Lecture de tout les auteurs
	@GetMapping
	public List<Author> getAllAuthor() {
		return authorRepository.findAll();
	}

	//////Update///////

	//Modification d'un auteur
	@PutMapping("{id}")
	public ResponseEntity<?> updateAuthor(@PathVariable(value = "id") int id, @Valid @RequestBody Author author) {
		Author authorTrouver = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("author", "id", id));

		authorTrouver.setFirstname(author.getFirstname());
		authorTrouver.setLastname(author.getLastname());
		authorTrouver.setNickname(author.getNickname());
		return new ResponseEntity<>(authorRepository.save(authorTrouver), HttpStatus.OK);
	}

	//////Delete///////

	//Suppression d'un auteur
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") int id) {
		Optional<Author> author = authorRepository.findById(id);
		if (!author.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			authorRepository.delete(author.orElseThrow(() -> new ResourceNotFoundException("author", "id", id)));
			authorRepository.flush();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
