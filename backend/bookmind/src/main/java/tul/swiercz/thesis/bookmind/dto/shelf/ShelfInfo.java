package tul.swiercz.thesis.bookmind.dto.shelf;

import tul.swiercz.thesis.bookmind.dto.InfoDto;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;

public class ShelfInfo extends InfoDto {

    private String name;
    private Iterable<BookInfo> books;

    //region Accessors
    public ShelfInfo() {
    }

    public ShelfInfo(String name, Iterable<BookInfo> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<BookInfo> getBooks() {
        return books;
    }

    public void setBooks(Iterable<BookInfo> books) {
        this.books = books;
    }
    //endregion

}
