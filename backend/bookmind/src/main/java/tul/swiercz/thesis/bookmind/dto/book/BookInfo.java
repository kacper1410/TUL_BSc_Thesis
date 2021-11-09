package tul.swiercz.thesis.bookmind.dto.book;

import tul.swiercz.thesis.bookmind.dto.AbstractDto;

public class BookInfo extends AbstractDto {

    private String title;

    //region Accessors
    public BookInfo() {
    }

    public BookInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //endregion

}
