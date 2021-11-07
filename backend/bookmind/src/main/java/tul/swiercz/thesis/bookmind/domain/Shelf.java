package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="bookmind_author")
public class Shelf extends AbstractDomain {

    private String name;

    @ManyToMany
    private List<Book> books;

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
