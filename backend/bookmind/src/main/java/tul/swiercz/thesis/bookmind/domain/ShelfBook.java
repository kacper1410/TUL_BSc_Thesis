package tul.swiercz.thesis.bookmind.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="bookmind_shelf_books")
public class ShelfBook extends AbstractVersionDomain {

    @Embeddable
    public static class ShelfBookId implements Serializable {

        @Column(name = "shelf_id")
        private Long shelfId;

        @Column(name = "books_id")
        private Long booksId;

        public ShelfBookId() {
        }

        public ShelfBookId(Long shelfId, Long booksId) {
            this.shelfId = shelfId;
            this.booksId = booksId;
        }

        public Long getShelfId() {
            return shelfId;
        }

        public void setShelfId(Long shelfId) {
            this.shelfId = shelfId;
        }

        public Long getBooksId() {
            return booksId;
        }

        public void setBooksId(Long bookId) {
            this.booksId = bookId;
        }
    }

    @EmbeddedId
    private ShelfBookId shelfBookId;

    @ManyToOne
    @MapsId("shelfId")
    private Shelf shelf;

    @ManyToOne
    @MapsId("booksId")
    private Book books;

    @ColumnDefault("true")
    private boolean active = true;

    public ShelfBook() {
    }

    public ShelfBook(Shelf shelf, Book books) {
        this.shelf = shelf;
        this.books = books;
        this.active = true;
        this.shelfBookId = new ShelfBookId(shelf.getId(), books.getId());
    }

    public ShelfBook(Shelf shelf, Book books, boolean active) {
        this.shelf = shelf;
        this.books = books;
        this.active = active;
        this.shelfBookId = new ShelfBookId(shelf.getId(), books.getId());
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Book getBook() {
        return books;
    }

    public void setBook(Book book) {
        this.books = book;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShelfBook)) return false;
        ShelfBook shelfBook = (ShelfBook) o;
        return shelf.equals(shelfBook.shelf) && books.equals(shelfBook.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelf, books);
    }
}
