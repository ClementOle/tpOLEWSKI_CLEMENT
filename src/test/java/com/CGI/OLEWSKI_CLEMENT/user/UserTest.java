package com.CGI.OLEWSKI_CLEMENT.user;

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
public class UserTest {

	@Autowired
	private UserRepository userRepository;

	//////Create///////

	//Test de création d'un user
	@Test
	public void testCreation() {
		//given
		User user = new User("Choupette du 63", null);

		//when
		userRepository.save(user);


		//then
		Assert.assertEquals(Optional.of(user), userRepository.findById(user.getId()));

		userRepository.delete(user);
		userRepository.flush();
	}

	//Test de création d'un user sans remplir les champs obligatoires
	@Test
	public void testCreationSansChampObligatoire() {
		//given
		User user = new User();
		try {

			//when
			userRepository.save(user);

			//then
			Assert.fail();
		} catch (ConstraintViolationException c) {
			Assert.assertTrue(true);
		}
	}

	//////Read///////

	//Test de récupération d'un user
	@Test
	public void read() {
		//given
		User user = new User("Clément Olewski", null);

		//when
		userRepository.save(user);

		//then
		Assert.assertEquals(user, userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId())));

		userRepository.delete(user);
		userRepository.flush();
	}


	//////Update///////

	//Test de modification d'un user
	@Test
	public void update() {
		//given
		User user = new User("Clément Olewski", null);
		userRepository.save(user);
		userRepository.flush();

		//when
		user.setFullname("Toto");
		userRepository.save(user);

		//then
		Assert.assertEquals(user, userRepository.findById(user.getId()).orElse(null));

		userRepository.delete(user);
		userRepository.flush();
	}

	//////Delete///////

	//Test de supprssion d'un user
	@Test
	public void testSuppression() {
		//given
		User user = new User("Toto", null);
		userRepository.save(user);
		userRepository.flush();

		//when
		userRepository.delete(user);
		userRepository.flush();

		//then
		Assert.assertNull(userRepository.findById(user.getId()).orElse(null));
	}

}
