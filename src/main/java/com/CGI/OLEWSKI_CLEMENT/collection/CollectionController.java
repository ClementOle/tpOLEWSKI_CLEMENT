package com.CGI.OLEWSKI_CLEMENT.collection;

import com.CGI.OLEWSKI_CLEMENT.book.Book;
import com.CGI.OLEWSKI_CLEMENT.book.BookRepository;
import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import com.CGI.OLEWSKI_CLEMENT.user.User;
import com.CGI.OLEWSKI_CLEMENT.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/collections")
public class CollectionController {

	@Autowired
	CollectionRepository collectionRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;

	//////Create///////

	//Création d'une collection
	@PostMapping
	public Collection postCollection(@Valid @RequestBody Collection collection) {
		User user = userRepository.findById(collection.getUser().getId()).orElseThrow(() -> new ResourceNotFoundException("id user", "id", collection.getUser().getId()));
		collection.setUser(user);
		return collectionRepository.save(collection);
	}

	//////Read///////

	//Récupération d'une collection
	@GetMapping("{id}")
	public Collection getCollection(@PathVariable(value = "id") int id) {
		return collectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group", "id", id));
	}

	//Récupération de toutes les collections
	@GetMapping
	public List<Collection> getAllCollection() {
		return collectionRepository.findAll();
	}

	//Récupération de toutes les collections par user
	@GetMapping("user/{idUser}")
	public List<Collection> getAllCollectionByUser(@PathVariable(value = "idUser") int idUser) {
		return collectionRepository.findAllByUser(userRepository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("user", "id", idUser)));
	}

	//////Update///////

	//Modification d'une collection
	@PutMapping("{id}")
	public ResponseEntity<?> updateCollection(@PathVariable(value = "id") int id, @Valid @RequestBody Collection collection) {
		Collection collectionTrouver = collectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("collection", "id", id));

		collectionTrouver.setTitle(collection.getTitle());
		collectionTrouver.setBooks(collection.getBooks());
		collectionTrouver.setUser(collection.getUser());
		return new ResponseEntity<>(collectionRepository.save(collectionTrouver), HttpStatus.OK);

	}

	//Ajout d'un livre à une collection en gérant si le livre n'est pas déjà dans une autre collection et en gérant si celui-ci n'est pas déjà dans celle-ci
	@PutMapping("{id}/book/{idBook}")
	public ResponseEntity<?> addBookToCollection(@PathVariable(value = "id") int id, @PathVariable(value = "idBook") int idBook) {
		Collection collectionTrouver = collectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("collection", "id", id));
		Book book = bookRepository.findById(idBook).orElseThrow(() -> new ResourceNotFoundException("book", "id", id));
		List<Book> books = collectionTrouver.getBooks();
		if (!books.contains(book) && book.getCollection() == null) {
			books.add(book);
			collectionTrouver.setBooks(books);
			book.setCollection(collectionTrouver);
			bookRepository.save(book);
			return new ResponseEntity<>(collectionRepository.save(collectionTrouver), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	//////Delete///////

	//Suppression d'une collection en modifiant l'attributs collection dans chacun des livres affectés
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCollection(@PathVariable(value = "id") int id) {
		Optional<Collection> collection = collectionRepository.findById(id);
		if (!collection.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (collection.get().getBooks() != null) {
				List<Book> books = collection.get().getBooks();
				for (Book book : books) {
					book.setCollection(null);
				}
			}
			collectionRepository.delete(collection.orElseThrow(() -> new ResourceNotFoundException("collection", "id", id)));
			collectionRepository.flush();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
