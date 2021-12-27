package tul.swiercz.thesis.bookmind.dto.book;

import tul.swiercz.thesis.bookmind.dto.ModifyDto;

public class ModifyBook extends ModifyDto {

    private String title;

    //region Accessors
    public ModifyBook() {
    }

    public ModifyBook(String title) {
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
