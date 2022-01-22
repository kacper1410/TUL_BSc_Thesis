package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="bookmind_shelf_action")
public class ShelfAction extends AbstractDomain {

    private ShelfActionType shelfActionType;

    private LocalDateTime actionDate;

    @ManyToOne
    private Shelf shelf;

    @ManyToOne
    private Book book;

    //region Accessors
    public ShelfAction() {
    }

    public ShelfAction(ShelfActionType shelfActionType, LocalDateTime actionDate, Shelf shelf, Book book) {
        this.shelfActionType = shelfActionType;
        this.actionDate = actionDate;
        this.shelf = shelf;
        this.book = book;
    }

    public ShelfActionType getShelfActionType() {
        return shelfActionType;
    }

    public void setShelfActionType(ShelfActionType shelfActionType) {
        this.shelfActionType = shelfActionType;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    //endregion
}
