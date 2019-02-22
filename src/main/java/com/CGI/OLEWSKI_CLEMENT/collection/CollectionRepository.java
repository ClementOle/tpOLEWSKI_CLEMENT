package com.CGI.OLEWSKI_CLEMENT.collection;

import com.CGI.OLEWSKI_CLEMENT.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {
	List<Collection> findAllByUser(User user);
}
