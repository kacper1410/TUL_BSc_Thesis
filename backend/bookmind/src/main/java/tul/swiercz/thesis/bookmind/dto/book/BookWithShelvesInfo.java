package tul.swiercz.thesis.bookmind.dto.book;

import tul.swiercz.thesis.bookmind.dto.InfoDto;
import tul.swiercz.thesis.bookmind.dto.shelf.ShelfForBookListInfo;

public class BookWithShelvesInfo extends InfoDto {

    private Iterable<ShelfForBookListInfo> shelves;

    private String title;

    private String author;

    //region Accessors
    public BookWithShelvesInfo() {
    }

    public BookWithShelvesInfo(String title, String author, Iterable<ShelfForBookListInfo> shelves) {
        this.title = title;
        this.author = author;
        this.shelves = shelves;
    }

    public Iterable<ShelfForBookListInfo> getShelves() {
        return shelves;
    }

    public void setShelves(Iterable<ShelfForBookListInfo> shelves) {
        this.shelves = shelves;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //endregion
}
