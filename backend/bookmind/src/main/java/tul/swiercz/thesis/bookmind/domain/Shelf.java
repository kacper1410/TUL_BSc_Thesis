package tul.swiercz.thesis.bookmind.domain;

import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="bookmind_shelf")
public class Shelf extends AbstractIdDomain {

    private String name;

    @OptimisticLock(excluded = true)
    @ManyToMany
    private Set<Book> books;

    @OneToMany(
            mappedBy = "shelf",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ShelfBook> shelfBooks;

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

    public Set<ShelfBook> getShelfBooks() {
        return shelfBooks;
    }

    public void setShelfBooks(Set<ShelfBook> shelfBook) {
        this.shelfBooks = shelfBook;
    }
    //endregion
}
