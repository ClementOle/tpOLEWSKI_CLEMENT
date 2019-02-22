package com.CGI.OLEWSKI_CLEMENT.collection;

import com.CGI.OLEWSKI_CLEMENT.exception.ResourceNotFoundException;
import com.CGI.OLEWSKI_CLEMENT.user.User;
import com.CGI.OLEWSKI_CLEMENT.user.UserRepository;
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
public class CollectionTest {

	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private UserRepository userRepository;

	//////Create///////

	//Test de création d'un user normalement
	@Test
	public void testCreation() {
		//given
		User user = new User("Zozo du 63", null);
		userRepository.save(user);
		Collection collection = new Collection("test", null, user);

		//when
		collectionRepository.save(collection);

		//then
		Assert.assertEquals(Optional.of(collection), collectionRepository.findById(collection.getId()));

		userRepository.delete(user);
		userRepository.flush();
		collectionRepository.delete(collection);

	}

	//Test de création d'un user sans remplir des champs obligatoires
	@Test
	public void testCreationSansChampObligatoire() {
		//given
		Collection collection = new Collection();
		try {

			//when
			collectionRepository.save(collection);

			//then
			Assert.fail();
		} catch (ConstraintViolationException c) {
			Assert.assertTrue(true);
		}
	}

	//////Read///////

	//Test de récupération d'une collection
	@Test
	public void read() {
		//given
		User user = new User("Lolo du 63", null);
		userRepository.save(user);
		Collection collection = new Collection("Collection de test", null, user);

		//when
		collectionRepository.save(collection);

		//then
		Assert.assertEquals(collection, collectionRepository.findById(collection.getId()).orElseThrow(() -> new ResourceNotFoundException("Collection", "id", collection.getId())));

		collectionRepository.delete(collection);
		collectionRepository.flush();

		userRepository.delete(user);
		userRepository.flush();
	}

	//////Update///////

	//Test de modification d'une collection
	@Test
	public void update() {
		//given
		User user = new User("Lolo du 63", null);
		userRepository.save(user);
		Collection collection = new Collection("Roman", null, user);
		collectionRepository.save(collection);

		//when
		collection.setTitle("Manga");
		collectionRepository.save(collection);

		//then
		Assert.assertEquals(collection, collectionRepository.findById(collection.getId()).orElse(null));

		collectionRepository.delete(collection);
		collectionRepository.flush();

		userRepository.delete(user);
		userRepository.flush();
	}

	//////Delete///////

	//Test de suppression d'une collection
	@Test
	public void testSuppression() {
		//given
		User user = new User("Zozo du 63", null);
		userRepository.save(user);
		Collection collection = new Collection("collection", null, user);
		collectionRepository.save(collection);
		collectionRepository.flush();

		//when
		collectionRepository.delete(collection);
		collectionRepository.flush();

		//then
		Assert.assertNull(collectionRepository.findById(collection.getId()).orElse(null));
		userRepository.delete(user);
		userRepository.flush();
	}

}
