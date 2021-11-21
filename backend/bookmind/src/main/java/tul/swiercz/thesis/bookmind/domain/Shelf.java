package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="bookmind_shelf")
public class Shelf extends AbstractDomain {

    private String name;

    @ManyToMany
    private List<Book> books;

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    //endregion

}
