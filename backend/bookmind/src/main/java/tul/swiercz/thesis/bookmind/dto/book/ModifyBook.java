package tul.swiercz.thesis.bookmind.dto.book;

import tul.swiercz.thesis.bookmind.dto.ModifyDto;

public class ModifyBook extends ModifyDto {

    private String title;

    private String author;

    //region Accessors
    public ModifyBook() {
    }

    public ModifyBook(String title, String author) {
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
