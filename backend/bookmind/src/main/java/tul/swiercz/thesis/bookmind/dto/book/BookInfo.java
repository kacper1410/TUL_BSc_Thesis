package tul.swiercz.thesis.bookmind.dto.book;

import tul.swiercz.thesis.bookmind.dto.InfoDto;

public class BookInfo extends InfoDto {

    private String title;

    private String author;

    //region Accessors
    public BookInfo() {
    }

    public BookInfo(String title, String author) {
        this.title = title;
        this.author = author;
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
