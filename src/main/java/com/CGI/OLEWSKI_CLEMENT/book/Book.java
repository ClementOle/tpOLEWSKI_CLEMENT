package com.CGI.OLEWSKI_CLEMENT.book;

import com.CGI.OLEWSKI_CLEMENT.author.Author;
import com.CGI.OLEWSKI_CLEMENT.collection.Collection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String title;
	private String description;
	@NotBlank
	private String categorie;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_AUTHOR"), nullable = false)
	@JsonIgnoreProperties("books")
	private Author author;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "collection_id", foreignKey = @ForeignKey(name = "FK_COLLECTION"))
	@JsonIgnoreProperties("books")
	private Collection collection;

	public Book(@NotBlank String title, String description, @NotBlank String categorie, Author author, Collection collection) {
		this.title = title;
		this.description = description;
		this.categorie = categorie;
		this.author = author;
		this.collection = collection;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (!Objects.equals(id, book.id)) return false;
		if (!Objects.equals(title, book.title)) return false;
		if (!Objects.equals(description, book.description)) return false;
		if (!Objects.equals(categorie, book.categorie)) return false;
		if (!Objects.equals(author, book.author)) return false;
		return Objects.equals(collection, book.collection);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (categorie != null ? categorie.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (collection != null ? collection.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", categorie='" + categorie + '\'' +
				", author=" + author +
				", collection=" + collection +
				'}';
	}
}
