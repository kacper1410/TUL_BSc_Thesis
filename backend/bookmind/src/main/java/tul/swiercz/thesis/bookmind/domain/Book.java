package tul.swiercz.thesis.bookmind.domain;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="bookmind_book")
public class Book extends AbstractDomain {

    private String title;

    @ManyToMany
    private List<Author> authors;

    //region Accessors
    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    //endregion

}
