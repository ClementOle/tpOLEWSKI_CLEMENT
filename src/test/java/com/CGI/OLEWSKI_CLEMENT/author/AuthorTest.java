package com.CGI.OLEWSKI_CLEMENT.author;

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
public class AuthorTest {

	@Autowired
	private AuthorRepository authorRepository;

	//////Create///////

	//Test de création d'un auteur
	@Test
	public void testCreation() {
		//given
		Author author = new Author("Jean", "Jack", "Jacky", null);

		//when
		authorRepository.save(author);

		//then
		Assert.assertEquals(Optional.of(author), authorRepository.findById(author.getId()));
		authorRepository.delete(author);
		authorRepository.flush();
	}

	//Test de création d'un auteur sans remplir les champs obligatoires
	@Test
	public void testCreationSansChampObligatoire() {
		//given
		Author author = new Author();
		try {

			//when
			authorRepository.save(author);

			//then
			Assert.fail();
		} catch (ConstraintViolationException c) {
			Assert.assertTrue(true);
		}
	}

	//////Read///////

	//Test de récupération d'un auteur
	@Test
	public void read() {
		//given
		Author author = new Author("Jack", "Jack", "Jackotte", null);

		//when
		authorRepository.save(author);

		//then
		Assert.assertEquals(author, authorRepository.findById(author.getId()).orElse(null));

		authorRepository.delete(author);
		authorRepository.flush();
	}

	//////Update///////

	//Test de modification d'un auteur
	@Test
	public void update() {
		//given
		Author author = new Author("Jean", "Jack", "Test", null);
		authorRepository.save(author);
		authorRepository.flush();

		//when
		author.setFirstname("Michel");
		author.setLastname("Michelin");
		author.setNickname("Michou");
		authorRepository.save(author);

		//then
		Assert.assertEquals(author, authorRepository.findById(author.getId()).orElse(null));

		authorRepository.delete(author);
		authorRepository.flush();
	}

	//////Delete///////

	//Test de suppression d'un auteur
	@Test
	public void testSuppression() {
		//given
		Author author = new Author("Jean", "Jack", "Jacky", null);
		authorRepository.save(author);
		authorRepository.flush();

		//when
		authorRepository.delete(author);
		authorRepository.flush();

		//then
		Assert.assertNull(authorRepository.findById(author.getId()).orElse(null));
	}
}
