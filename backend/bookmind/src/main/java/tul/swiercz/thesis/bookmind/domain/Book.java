package tul.swiercz.thesis.bookmind.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bookmind_book")
public class Book extends AbstractDomain {

    private String title;

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
}
