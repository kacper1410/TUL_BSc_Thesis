package tul.swiercz.thesis.bookmind.dto.shelf;

import tul.swiercz.thesis.bookmind.dto.InfoDto;
import tul.swiercz.thesis.bookmind.dto.book.BookOnShelfListInfo;

public class ShelfInfo extends InfoDto {

    private String name;
    private Iterable<BookOnShelfListInfo> books;

    //region Accessors
    public ShelfInfo() {
    }

    public ShelfInfo(String name, Iterable<BookOnShelfListInfo> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<BookOnShelfListInfo> getBooks() {
        return books;
    }

    public void setBooks(Iterable<BookOnShelfListInfo> books) {
        this.books = books;
    }
    //endregion

}
