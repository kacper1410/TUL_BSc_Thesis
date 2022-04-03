package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="bookmind_shelf")
public class Shelf extends AbstractDomain {

    private String name;

    @ManyToMany
    private Set<Book> books;

    @ManyToOne
    private User user;

    //region Accessors
    public Shelf() {
    }

    public Shelf(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shelf)) return false;
        if (!super.equals(o)) return false;
        Shelf shelf = (Shelf) o;
        return Objects.equals(name, shelf.name) && Objects.equals(books, shelf.books) && Objects.equals(user, shelf.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, books, user);
    }
    //endregion

}
