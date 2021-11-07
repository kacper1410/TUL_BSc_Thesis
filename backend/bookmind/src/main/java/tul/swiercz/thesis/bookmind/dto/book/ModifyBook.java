package tul.swiercz.thesis.bookmind.dto.book;

public class ModifyBook {

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
