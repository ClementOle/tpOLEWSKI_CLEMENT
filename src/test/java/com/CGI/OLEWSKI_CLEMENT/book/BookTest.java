package com.CGI.OLEWSKI_CLEMENT.book;

import com.CGI.OLEWSKI_CLEMENT.author.Author;
import com.CGI.OLEWSKI_CLEMENT.author.AuthorRepository;
import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookTest {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	//////Create///////

	//Test création d'un livre
	@Test
	public void create() {
		//given
		Author author = new Author("Jack", "Jack", "Jackotte", null);
		authorRepository.save(author);
		Book book = new Book("Titre", "Description", "Romantic", author, null);

		//when
		bookRepository.save(book);

		//then
		Assert.assertEquals(Optional.of(book), bookRepository.findById(book.getId()));

		bookRepository.delete(book);
		bookRepository.flush();

		authorRepository.delete(author);
		authorRepository.flush();
	}

	//Test création d'un livre sans remplir les champs obligatoires
	@Test
	public void testCreationSansChampObligatoire() {
		//given
		Book book = new Book();
		try {

			//when
			bookRepository.save(book);

			//then
			Assert.fail();
		} catch (ConstraintViolationException c) {
			Assert.assertTrue(true);
		}
	}

	//////Read///////

	//Test récupération d'un livre
	@Test
	public void read() {
		//given
		Author author = new Author("Jack", "Jack", "Jackotte", null);
		authorRepository.save(author);

		//when
		Book book = new Book("titre", "description", "categorie", author, null);
		bookRepository.save(book);


		//then
		Assert.assertEquals(book, bookRepository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("Book", "id", book.getId())));

		authorRepository.delete(author);
		authorRepository.flush();

		bookRepository.delete(book);
		bookRepository.flush();

	}

	//////Update///////

	//Test modification d'un livre
	@Test
	public void update() {
		//given
		Author author = new Author("Lolo du 63", "fdfdsfs", "fdsfdsf", null);
		authorRepository.save(author);
		Book book = new Book("fdsfdsf", "fdsfdsfsdf", "fdfdsfds", author, null);
		bookRepository.save(book);

		//when
		book.setTitle("fdiofkds,fokdsf,dsokfd");
		bookRepository.save(book);

		//then
		Assert.assertEquals(book, bookRepository.findById(book.getId()).orElse(null));

		bookRepository.delete(book);
		bookRepository.flush();

		authorRepository.delete(author);
		authorRepository.flush();
	}

	//////Delete///////

	//Test de suppression d'un livre
	@Test
	public void testSuppression() {
		//given
		Author author = new Author("Jack", "Jack", "Jackotte", null);
		authorRepository.save(author);
		Book book = new Book("Martine à la plage", "dafefds", "Romantic", author, null);
		bookRepository.save(book);
		bookRepository.flush();

		//when
		bookRepository.delete(book);
		bookRepository.flush();

		//then
		Assert.assertNull(bookRepository.findById(book.getId()).orElse(null));

		authorRepository.delete(author);
		authorRepository.flush();
	}


}
