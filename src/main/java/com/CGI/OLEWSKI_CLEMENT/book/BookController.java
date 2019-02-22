package com.CGI.OLEWSKI_CLEMENT.book;

import com.CGI.OLEWSKI_CLEMENT.author.Author;
import com.CGI.OLEWSKI_CLEMENT.author.AuthorRepository;
import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;

	//////Create///////

	//Création d'un book avec vérification de la categorie
	@PostMapping
	public ResponseEntity<?> postBook(@Valid @RequestBody Book book) {
		Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResourceNotFoundException("id author", "id", book.getAuthor().getId()));
		book.setAuthor(author);
		List<String> listCategorie = new ArrayList<String>() {{
			add("Roman");
			add("Manga");
			add("Comic");
			add("Documentaire");
		}};
		if (listCategorie.contains(book.getCategorie())) {
			return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("This is not a valid category !", HttpStatus.I_AM_A_TEAPOT);
		}
	}

	//////Read///////

	//Récupération d'un livre
	@GetMapping("{id}")
	public Book getBook(@PathVariable(value = "id") int id) {
		return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group", "id", id));
	}

	//Récupération de tout les livres avec possibilité d'y récupéré par odre de titre en ajoutant ?trier=true à l'url
	@GetMapping
	public List<Book> getAllBook(@RequestParam(value = "trier", defaultValue = "false") boolean trier) {
		if (trier) {
			return bookRepository.findAllByOrderByTitle();
		}
		return bookRepository.findAll();
	}

	//////Update///////

	//Modification d'un livre avec controle de la catégorie
	@PutMapping("{id}")
	public ResponseEntity<?> updateBook(@PathVariable(value = "id") int id, @Valid @RequestBody Book book) {
		Book bookTrouver = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book", "id", id));

		bookTrouver.setTitle(book.getTitle());
		bookTrouver.setDescription(book.getDescription());
		bookTrouver.setCategorie(book.getCategorie());
		bookTrouver.setAuthor(book.getAuthor());
		bookTrouver.setCollection(book.getCollection());
		List<String> listCategorie = new ArrayList<String>() {{
			add("Roman");
			add("Manga");
			add("Comic");
			add("Documentaire");
		}};
		if (listCategorie.contains(book.getCategorie())) {
			return new ResponseEntity<>(bookRepository.save(bookTrouver), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("This is not a valid category !", HttpStatus.I_AM_A_TEAPOT);
		}

	}

	//////Delete///////

	//Suppression d'un livre
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBook(@PathVariable(value = "id") int id) {
		Optional<Book> book = bookRepository.findById(id);
		if (!book.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			bookRepository.delete(book.orElseThrow(() -> new ResourceNotFoundException("book", "id", id)));
			bookRepository.flush();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}


}
