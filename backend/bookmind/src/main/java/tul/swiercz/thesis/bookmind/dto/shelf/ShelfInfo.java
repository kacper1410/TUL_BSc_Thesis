package tul.swiercz.thesis.bookmind.dto.shelf;

import tul.swiercz.thesis.bookmind.dto.InfoDto;
import tul.swiercz.thesis.bookmind.dto.book.BookListInfo;

public class ShelfInfo extends InfoDto {

    private String name;
    private Iterable<BookListInfo> books;

    //region Accessors
    public ShelfInfo() {
    }

    public ShelfInfo(String name, Iterable<BookListInfo> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<BookListInfo> getBooks() {
        return books;
    }

    public void setBooks(Iterable<BookListInfo> books) {
        this.books = books;
    }
    //endregion

}
