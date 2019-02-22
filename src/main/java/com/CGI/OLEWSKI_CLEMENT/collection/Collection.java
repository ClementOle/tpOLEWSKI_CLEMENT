package com.CGI.OLEWSKI_CLEMENT.collection;

import com.CGI.OLEWSKI_CLEMENT.book.Book;
import com.CGI.OLEWSKI_CLEMENT.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Collection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String title;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "collection")
	@JsonIgnoreProperties("collection")
	private List<Book> books;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER"), nullable = false)
	@JsonIgnoreProperties("collections")
	private User user;

	public Collection(@NotBlank String title, List<Book> books, User user) {
		this.title = title;
		this.books = books;
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Collection that = (Collection) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(title, that.title) &&
				Objects.equals(books, that.books) &&
				Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, books, user);
	}

	@Override
	public String toString() {
		return "collection{" +
				"id=" + id +
				", title='" + title + '\'' +
				", books=" + books +
				", user=" + user +
				'}';
	}
}
