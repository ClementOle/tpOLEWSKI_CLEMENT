package com.CGI.OLEWSKI_CLEMENT.user;

import com.CGI.OLEWSKI_CLEMENT.collection.Collection;
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
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String fullname;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnoreProperties("user")
	private List<Collection> collections;

	public User(@NotBlank String fullname, List<Collection> collections) {
		this.fullname = fullname;
		this.collections = collections;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(fullname, user.fullname) &&
				Objects.equals(collections, user.collections);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullname, collections);
	}

	@Override
	public String toString() {
		return "user{" +
				"id=" + id +
				", fullname='" + fullname + '\'' +
				", collections=" + collections +
				'}';
	}
}
