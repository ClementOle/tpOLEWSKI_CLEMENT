package com.CGI.OLEWSKI_CLEMENT.author;


import com.CGI.OLEWSKI_CLEMENT.book.Book;
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
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
	private String nickname;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("author")
	private List<Book> books;

	public Author(@NotBlank String firstname, @NotBlank String lastname, String nickname, List<Book> books) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.nickname = nickname;
		this.books = books;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Author author = (Author) o;
		return Objects.equals(id, author.id) &&
				Objects.equals(firstname, author.firstname) &&
				Objects.equals(lastname, author.lastname) &&
				Objects.equals(nickname, author.nickname) &&
				Objects.equals(books, author.books);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstname, lastname, nickname, books);
	}

	@Override
	public String toString() {
		return "author{" +
				"id=" + id +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", nickname='" + nickname + '\'' +
				", books=" + books +
				'}';
	}
}
